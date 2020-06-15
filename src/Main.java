//import ctrl.Controller;
//import model.expressions.*;
//import model.operators.ArithOp;
//import model.operators.RelOp;
//import model.state.PrgState;
//import model.statements.*;
//import model.types.BoolType;
//import model.types.IntType;
//import model.types.RefType;
//import model.types.StringType;
//import model.values.BoolValue;
//import model.values.IntValue;
//import model.values.StringValue;
//import repo.IRepo;
//import repo.Repository;
//import view.ClearLogsCommand;
//import view.ExitCommand;
//import view.RunCommand;
//import view.TextMenu;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        ///1.
//        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
//                    new PrintStmt(new VarExp("v"))));
//
//        PrgState prg1 = new PrgState(ex1);
//        IRepo repo1 = new Repository("logs1.txt");
//        repo1.addProgram(prg1);
//        Controller ctr1 = new Controller(repo1);
//
//        ///2.
//        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
//                    new CompStmt(new VarDeclStmt("b",new IntType()),
//                    new CompStmt(new AssignStmt("a", new ArithExp(ArithOp.PLUS,new ValueExp(new IntValue(2)), new ArithExp(ArithOp.MULTIPLY,new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
//                    new CompStmt(new AssignStmt("b",new ArithExp(ArithOp.PLUS,new VarExp("a"), new ValueExp(new IntValue(1)))),
//                    new PrintStmt(new VarExp("b"))))));
//
//        PrgState prg2 = new PrgState(ex2);
//        IRepo repo2 = new Repository("logs2.txt");
//        repo2.addProgram(prg2);
//        Controller ctr2 = new Controller(repo2);
//
//        ///3.
//        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
//                    new CompStmt(new VarDeclStmt("v", new IntType()),
//                    new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
//                    new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
//                    new PrintStmt(new VarExp("v"))))));
//
//        PrgState prg3 = new PrgState(ex3);
//        IRepo repo3 = new Repository("logs3.txt");
//        repo3.addProgram(prg3);
//        Controller ctr3 = new Controller(repo3);
//
//        ///4.
//        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
//                    new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
//                    new CompStmt(new OpenRFileStmt(new VarExp("varf")),
//                    new CompStmt(new VarDeclStmt("varc", new IntType()),
//                    new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
//                    new CompStmt(new PrintStmt(new VarExp("varc")),
//                    new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
//                    new CompStmt(new PrintStmt(new VarExp("varc")),
//                    new CloseRFileStmt(new VarExp("varf"))))))))));
//
//        PrgState prg4 = new PrgState(ex4);
//        IRepo repo4 = new Repository("logs4.txt");
//        repo4.addProgram(prg4);
//        Controller ctr4 = new Controller(repo4);
//
//        ///5.
//        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                    new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(20))),
//                    new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
//                    new CompStmt(new HeapAllocStmt("a", new VarExp("v")),
//                    new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(30))),
//                    new CompStmt(new HeapWriteStmt("v", new ValueExp(new IntValue(40))),
//                    new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))))))));
//
//
//        PrgState prg5 = new PrgState(ex5);
//        IRepo repo5 = new Repository("logs5.txt");
//        repo5.addProgram(prg5);
//        Controller ctr5 = new Controller(repo5);
//
//        ///6.
//        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
//                    new CompStmt(new WhileStmt(new RelExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
//                    new CompStmt(new PrintStmt(new VarExp("v")),
//                    new AssignStmt("v", new ArithExp(ArithOp.MINUS, new VarExp("v"), new ValueExp(new IntValue(1)))))),
//                    new PrintStmt(new VarExp("v")))));
//
//        PrgState prg6 = new PrgState(ex6);
//        IRepo repo6 = new Repository("logs6.txt");
//        repo6.addProgram(prg6);
//        Controller ctr6 = new Controller(repo6);
//
//        ///7.
//        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                    new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
//                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
//                    new CompStmt(new HeapAllocStmt("a", new ValueExp(new IntValue(22))),
//                    new CompStmt(new ForkStmt(
//                            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
//                            new CompStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))),
//                            new CompStmt(new PrintStmt(new VarExp("v")),
//                            new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
//                    new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("a"))),
//                    new PrintStmt(new VarExp("v"))))))));
//
//        PrgState prg7 = new PrgState(ex7);
//        IRepo repo7 = new Repository("logs7.txt");
//        repo7.addProgram(prg7);
//        Controller ctr7 = new Controller(repo7);
//
//        ///8.
//        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new StringType()),
//                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
//                        new PrintStmt(new VarExp("v"))));
//
//        PrgState prg8 = new PrgState(ex8);
//        IRepo repo8 = new Repository("logs8.txt");
//        repo8.addProgram(prg8);
//        Controller ctr8 = new Controller(repo8);
//
//        TextMenu menu = new TextMenu();
//        menu.addCommand(new ExitCommand("0", "exit"));
//        menu.addCommand(new RunCommand(String.valueOf(menu.getMenuSize()), ex1.toString(), ctr1));
//        menu.addCommand(new RunCommand(String.valueOf(menu.getMenuSize()), ex2.toString(), ctr2));
//        menu.addCommand(new RunCommand(String.valueOf(menu.getMenuSize()), ex3.toString(), ctr3));
//        menu.addCommand(new RunCommand(String.valueOf(menu.getMenuSize()), ex4.toString(), ctr4));
//        menu.addCommand(new RunCommand(String.valueOf(menu.getMenuSize()), ex5.toString(), ctr5));
//        menu.addCommand(new RunCommand(String.valueOf(menu.getMenuSize()), ex6.toString(), ctr6));
//        menu.addCommand(new RunCommand(String.valueOf(menu.getMenuSize()), ex7.toString(), ctr7));
//        menu.addCommand(new RunCommand(String.valueOf(menu.getMenuSize()), ex8.toString(), ctr8));
//
//        menu.addCommand(new ClearLogsCommand(String.valueOf(menu.getMenuSize()), "Clear all the logs in the project directory", menu.getMenuSize() - 1));
//
//        menu. show();
//    }
//
//
//}
