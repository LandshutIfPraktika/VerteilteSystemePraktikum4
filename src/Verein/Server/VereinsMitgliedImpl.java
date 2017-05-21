package Verein.Server;

import Verein.VereinsMitgliedPackage.verein;
import Verein._VereinsMitgliedImplBase;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by s-gheldd on 21.05.17.
 */
public class VereinsMitgliedImpl extends _VereinsMitgliedImplBase {

    private final String mName;
    private final HashMap<String, verein> vereine;

    public VereinsMitgliedImpl(final String mName, final Iterable<verein> vereine) {
        super();

        this.mName = mName;
        this.vereine = new HashMap<>();

        for (final verein verein : vereine) {
            this.vereine.put(verein.vname, verein);
        }
    }

    @Override
    public String mname() {
        return this.mName;
    }

    @Override
    public verein[] mvereine() {
        return this.vereine.values().toArray(new verein[this.vereine.size()]);
    }

    @Override
    public short erhoeheBeitrag(final String verein, final short erhoehung) {
        final verein gesucht = this.vereine.get(verein);
        if (verein != null) {
            gesucht.vbeitrag += erhoehung;
            return gesucht.vbeitrag;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "VereinsMitgliedImpl{" +
                "mName='" + mName + '\'' +
                ", vereine=" + Arrays.toString(vereine.values().toArray(new verein[vereine.size()])) +
                '}';
    }
}
