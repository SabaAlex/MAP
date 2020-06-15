package ctrl;

import exceptions.MyException;
import model.heap.MyHeap;
import model.heap.MyIHeap;
import model.state.PrgState;
import model.symTable.MyDictionary;
import model.symTable.MyIDictionary;
import model.types.Type;
import model.values.IntValue;
import model.values.RefValue;
import model.values.Value;
import repo.IRepo;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    IRepo repo;
    ExecutorService executor;

    public Controller(IRepo repo) {
        this.repo = repo;
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList())
                ;
    }

    List<Integer> getAddrFromAllSymTablesAndHeap(Map<Integer,Value> heap){
        return Stream.concat(
                repo.getPrgList().stream()
                .map(prg -> getAddrFromSymTable(prg.getSymTable().getContainer().values()).stream())
                .reduce(Stream.of(0), Stream::concat)
                ,
                heap.values()
                .stream()
                .map(v -> {IntValue val = (IntValue)v; return val.getVal();})
                ).collect(Collectors.toList());
    }


    Map<Integer,Value> safeGarbageCollector(List<Integer> symTblAndHeapAddr, Map<Integer,Value> heap){
        return heap.entrySet()
                .stream()
                .filter(e -> symTblAndHeapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                ;
    }

    void conservativeGarbageCollector(){
        repo.getPrgList().get(0)
                .setHeap(new MyHeap((HashMap)safeGarbageCollector(getAddrFromAllSymTablesAndHeap(repo.getPrgList().get(0).getHeap().getContainer()),
                        repo.getPrgList().get(0).getHeap().getContainer())));
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList())
                ;
    }

    public void oneStepForAllPrg(List<PrgState> prgList) {

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList())
                ;

        List<PrgState> newPrgList;

        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException ignored) {
                            return null;
                        }
                    }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        catch (InterruptedException e){
            throw new MyException(e.getMessage());
        }

        prgList.addAll(newPrgList);
        prgList.forEach(prg -> repo.logPrgStateExec(prg));
        repo.setPrgList(prgList);
    }

    public void resetProgram(){
        repo.getPrgList().forEach(PrgState::resetStateToOriginal);
    }

    public void oneStep(){
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        prgList.forEach(prg -> repo.logPrgStateExec(prg));

        if (!prgList.isEmpty()){
            conservativeGarbageCollector();
            oneStepForAllPrg(prgList);
        }

        executor.shutdown();
        repo.setPrgList(prgList);
    }

    public void removeAfterOneStep()
    {
        repo.setPrgList(removeCompletedPrg(repo.getPrgList()));
    }

    public void allStep() {

        MyIDictionary<String, Type> typeEnvironment = new MyDictionary<>();
        repo.getPrgList().forEach(prg -> prg.getOriginalProgram().typeCheck(typeEnvironment));

        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

        prgList.forEach(prg -> repo.logPrgStateExec(prg));

        while(prgList.size() > 0){
            ///call garbage collector

            conservativeGarbageCollector();

            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();

        repo.setPrgList(prgList);
        resetProgram();
    }

    public IRepo getRepo() {
        return repo;
    }
}
