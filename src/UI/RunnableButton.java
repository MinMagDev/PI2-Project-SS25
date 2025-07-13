package UI;

import javax.swing.*;

public class RunnableButton extends JButton {
    public RunnableButton(String text, Reference<Runnable> action) {
        super(text);
        setAlignmentX(CENTER_ALIGNMENT);
        addActionListener(e ->{
            action.get().run();
        });
    }
}
