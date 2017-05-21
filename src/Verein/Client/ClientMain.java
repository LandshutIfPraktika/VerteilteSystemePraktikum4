package Verein.Client;

import Verein.VereinsMitglied;
import Verein.VereinsMitgliedHelper;
import Verein.VereinsMitgliedPackage.verein;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.*;

import java.util.Scanner;

/**
 * Created by s-gheldd on 21.05.17.
 */
public class ClientMain {
    public static void main(String[] args) throws Exception {
        final ORB orb = ORB.init(args, null);

        final Object nameService = orb.resolve_initial_references("NameService");

        final NamingContextExt namingContextExt = NamingContextExtHelper.narrow(nameService);
        final Scanner scanner = new Scanner(System.in);

        int code = 1;
        while (code != 0) {
            System.out.println("Finde Mitglied (1), erhöhe Beitrag (2), alle Mitglieder in Verein (3), quit (0)");

            code = scanner.nextInt();

            switch (code) {
                case 1: {
                    System.out.print("CORBA Name des Mitglied: ");
                    final String name = scanner.next().trim();
                    final Object object = namingContextExt.resolve_str(name);
                    final VereinsMitglied vereinsMitglied = VereinsMitgliedHelper.narrow(object);
                    System.out.println(vereinsMitglied.mname());
                    for (verein verein : vereinsMitglied.mvereine()) {
                        System.out.println(verein.vname + ":" + verein.vbeitrag);
                    }
                }
                break;
                case 2: {
                    System.out.print("CORBA Name des Mitglied: ");
                    final String name = scanner.next().trim();
                    final String verein = name.split("/")[1];
                    final Object object = namingContextExt.resolve_str(name);
                    final VereinsMitglied vereinsMitglied = VereinsMitgliedHelper.narrow(object);
                    System.out.print("Betrag erhöhen um: ");
                    final short betrag = scanner.nextShort();
                    System.out.println("Neuer Betrag: " + vereinsMitglied.erhoeheBeitrag(verein, betrag));
                }
                break;
                case 3: {

                    System.out.print("CORBA Name des Vereins: ");
                    final String verein = scanner.next().trim();
                    final Object vereinObject = namingContextExt.resolve_str(verein);

                    final NamingContext vereinContext = NamingContextHelper.narrow(vereinObject);

                    final BindingListHolder bindingListHolder = new BindingListHolder();
                    final BindingIteratorHolder bindingIteratorHolder = new BindingIteratorHolder();
                    vereinContext.list(0, bindingListHolder, bindingIteratorHolder);
                    final BindingIterator iterator = bindingIteratorHolder.value;
                    final BindingHolder bindingHolder = new BindingHolder();

                    while (iterator.next_one(bindingHolder)) {
                        final NameComponent[] binding_name = bindingHolder.value.binding_name;
                        final Object mitgliedContext = vereinContext.resolve(binding_name);
                        final VereinsMitglied vereinsMitglied = VereinsMitgliedHelper.narrow(mitgliedContext);

                        System.out.println(vereinsMitglied.mname());

                    }

                    iterator.destroy();
                }
                break;

                default:
                    break;
            }


        }

    }
}
