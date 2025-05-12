package io.asirum.EventListener;

import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import io.asirum.Service.ApplicationContext;

public class BaseHandler {
    private ApplicationContext context;

    public BaseHandler() {
        context = ApplicationContext.getInstance();
    }

    public Runnable onPush(ManagedScreen screen, ScreenTransition transition) {
        return () -> context.pushScreen(screen, transition);
    }

}
