package Verein.Server;

import Verein.VereinsMitglied;
import Verein.VereinsMitgliedPackage.verein;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

import java.util.Arrays;

/**
 * Created by s-gheldd on 21.05.17.
 */
public class ServerMain {


    public static void main(String[] args) throws Exception {


        final ORB orb = ORB.init(args, null);

        final Object nameService = orb.resolve_initial_references("NameService");
        final NamingContext initialReference = NamingContextHelper.narrow(nameService);


        final NameComponent hamburgComponent = new NameComponent("Hamburg", "");
        final NameComponent bremenComponent = new NameComponent("Bremen", "");

        final NamingContext hamburgContext = initialReference.bind_new_context(new NameComponent[]{hamburgComponent});
        final NamingContext bremenContext = initialReference.bind_new_context(new NameComponent[]{bremenComponent});


        final NameComponent hansakulturComponent = new NameComponent("Hansakultur", "");
        final NameComponent hansasportComponent = new NameComponent("Hansasport", "");
        final NameComponent hansahilfeComponent = new NameComponent("Hansahilfe", "");

        final NameComponent werdersportComponent = new NameComponent("Werdersport", "");
        final NameComponent werdertheaterComponent = new NameComponent("Werdertheater", "");

        final NamingContext hansakultorContext = hamburgContext.bind_new_context(new NameComponent[]{hansakulturComponent});
        final NamingContext hansasportContext = hamburgContext.bind_new_context(new NameComponent[]{hansasportComponent});
        final NamingContext hansahilfeContext = hamburgContext.bind_new_context(new NameComponent[]{hansahilfeComponent});

        final NamingContext werdersportContext = bremenContext.bind_new_context(new NameComponent[]{werdersportComponent});
        final NamingContext werdertheaterContext = bremenContext.bind_new_context(new NameComponent[]{werdertheaterComponent});

        final VereinsMitglied peterson = new VereinsMitgliedImpl("Peterson",
                Arrays.asList(new verein("Hansakultur", (short) 10), new verein("Hansahilfe", (short) 15), new verein("Werdersport", (short) 25)));
        final NameComponent petersonComponent = new NameComponent("Peterson", "");
        orb.connect(peterson);
        hansakultorContext.bind(new NameComponent[]{petersonComponent}, peterson);
        hansahilfeContext.bind(new NameComponent[]{petersonComponent}, peterson);
        werdersportContext.bind(new NameComponent[]{petersonComponent}, peterson);


        final VereinsMitglied sams = new VereinsMitgliedImpl("Sams",
                Arrays.asList(new verein("Hansasport", (short) 25), new verein("Werdertheater", (short) 100)));
        final NameComponent samsComponent = new NameComponent("Sams", "");
        orb.connect(sams);
        hansasportContext.bind(new NameComponent[]{samsComponent}, sams);
        werdertheaterContext.bind(new NameComponent[]{samsComponent}, sams);

        final java.lang.Object lock = new java.lang.Object();

        synchronized (lock) {
            lock.wait();
        }

    }
}
