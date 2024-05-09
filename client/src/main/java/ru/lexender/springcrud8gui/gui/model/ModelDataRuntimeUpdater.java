package ru.lexender.springcrud8gui.gui.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8gui.gui.BaseFrame;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class ModelDataRuntimeUpdater implements Runnable {
    BaseFrame baseFrame;

    public ModelDataRuntimeUpdater(BaseFrame baseFrame) {
        this.baseFrame = baseFrame;

        new Thread(this, "Updater").start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(5000);
                baseFrame.refreshTable();
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }
}
