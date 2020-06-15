package view;

import ctrl.Controller;
import exceptions.MyException;

import java.util.Vector;

public class RunCommand extends Command {

    private Controller ctrl;
    public RunCommand(String key, String desc,Controller ctr) {

        super(key, desc);
        this.ctrl=ctr;
    }


    @Override
    public void execute()
    {
        ///Vector<String> To_print = ctrl.allStep();

        ctrl.allStep();
        ctrl.resetProgram();

        /*
        try {
            for (String item : To_print)
                System.out.println(item);
        }
        catch (MyException exceptions) {
            System.out.println(exceptions.getMessage());
        }
        */
    }
}
