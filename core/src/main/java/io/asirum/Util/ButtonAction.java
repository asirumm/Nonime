package io.asirum.Util;

import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import io.asirum.Service.ApplicationContext;

import java.util.function.Supplier;

public class ButtonAction {
    private static final ApplicationContext context = ApplicationContext.getInstance();

    /**
     * penggunaan : onPush(LevelScreen::new, null));
     *
     * agar tidak langsung dieksekusi, karena banyak screen yang menggunakan
     * constructor sebagai inisialisasi data dan sebagainya.
     * Dikhawatirkan seharusnya belum berpindah tetapi sudah berpindah
     * atau proses sudah dijalankan
     */
    public static Runnable switchScreen(Supplier<ManagedScreen> screen, ScreenTransition transition) {
        return () -> context.pushScreen(screen.get(), transition);
    }

}
