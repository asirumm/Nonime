package io.asirum.Util;

import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import io.asirum.Service.ApplicationContext;

import java.util.function.Supplier;

public class ButtonActionHelper {
    private ApplicationContext context;

    public ButtonActionHelper() {
        context = ApplicationContext.getInstance();
    }

    public Runnable onPush(Supplier<ManagedScreen> screen, ScreenTransition transition) {
        return () -> context.pushScreen(screen.get(), transition);
    }

}
