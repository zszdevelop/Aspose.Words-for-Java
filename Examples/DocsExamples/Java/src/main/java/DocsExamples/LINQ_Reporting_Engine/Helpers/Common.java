package DocsExamples.LINQ_Reporting_Engine.Helpers;

import DocsExamples.DocsExamplesBase;
import DocsExamples.LINQ_Reporting_Engine.Helpers.Data_Source_Objects.Manager;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import DocsExamples.LINQ_Reporting_Engine.Helpers.Data_Source_Objects.Client;
import DocsExamples.LINQ_Reporting_Engine.Helpers.Data_Source_Objects.Contract;

public class Common extends DocsExamplesBase
{
    /// <summary>
    /// Return the first manager from Managers, which is an enumeration of instances of the Manager class. 
    /// </summary>        
    public static Manager getManager() throws Exception
    {
        //ExStart:GetManager
        Iterator<Manager> managers = getManagers().iterator();
        managers.hasNext();
        
        return managers.next();
        //ExEnd:GetManager
    }

    /// <summary>
    /// Return an enumeration of instances of the Client class. 
    /// </summary>        
    public static Iterable<Client> getClients() throws Exception
    {
        //ExStart:GetClients
        for (Manager manager : getManagers())
        {
            for (Contract contract : manager.getContracts())
               return (Iterable<Client>) contract.getClient();
        }
        //ExEnd:GetClients

        return null;
    }

    /// <summary>
    ///  Return an enumeration of instances of the Manager class.
    /// </summary>
    public static Iterable<Manager> getManagers() throws Exception
    {
        //ExStart:GetManagers
        // --------------------------------------------------
        // First manager
        // --------------------------------------------------
        Manager firstManager = new Manager();
        firstManager.setName("John Smith");
        firstManager.setAge(36);

        ArrayList<Contract> contracts = new ArrayList<>();
        {
            contracts.add(new Contract());
            {
                contracts.get(0).setManager(firstManager);
                contracts.get(0).setPrice(1200000f);
                contracts.get(0).setDate(LocalDate.of(2017, 1, 1));
                contracts.get(0).setClient(new Client("A Company", "Australia", "219-241 Cleveland St STRAWBERRY HILLS  NSW  1427"));
            }
            contracts.add(new Contract());
            {
                contracts.get(1).setManager(firstManager);
                contracts.get(1).setPrice(750000f);
                contracts.get(1).setDate(LocalDate.of(2017, 4, 1));
                contracts.get(1).setClient(new Client("B Ltd.", "Brazil", "Avenida João Jorge, 112, ap. 31 Vila Industrial Campinas - SP 13035-680"));
            }
            contracts.add(new Contract());
            {
                contracts.get(2).setManager(firstManager);
                contracts.get(2).setPrice(350000f);
                contracts.get(2).setDate(LocalDate.of(2017, 7, 1));
                contracts.get(2).setClient(new Client("C & D", "Canada", "101-3485 RUE DE LA MONTAGNE MONTRÉAL (QUÉBEC) H3G 2A6"));
            }
        }

        firstManager.setContracts(contracts);

        // --------------------------------------------------
        // Second manager
        // --------------------------------------------------
        Manager secondManager = new Manager();
        secondManager.setName("Tony Anderson");
        secondManager.setAge(37);

        contracts = new ArrayList<>();
        {
            contracts.add(new Contract());
            {
                contracts.get(0).setManager(secondManager);
                contracts.get(0).setPrice(650000f);
                contracts.get(0).setDate(LocalDate.of(2017, 2, 1));
                contracts.get(0).setClient(new Client("E Corp.", "445 Mount Eden Road Mount Eden Auckland 1024"));
            }
            contracts.add(new Contract());
            {
                contracts.get(1).setManager(secondManager);
                contracts.get(1).setPrice(550000f);
                contracts.get(1).setDate(LocalDate.of(2017, 8, 1));
                contracts.get(1).setClient(new Client("F & Partners", "20 Greens Road Tuahiwi Kaiapoi 7691"));
            }
        }

        secondManager.setContracts(contracts);

        // --------------------------------------------------
        // Third manager
        // --------------------------------------------------
        Manager thirdManager = new Manager();
        thirdManager.setName("July James");
        thirdManager.setAge(38);

        contracts = new ArrayList<>();
        {
            contracts.add(new Contract());
            {
                contracts.get(0).setManager(thirdManager);
                contracts.get(0).setPrice(350000f);
                contracts.get(0).setDate(LocalDate.of(2017, 2, 1));
                contracts.get(0).setClient(new Client("G & Co.", "Greece", "Karkisias 6 GR-111 42  ATHINA GRÉCE"));
            }
            contracts.add(new Contract());
            {
                contracts.get(1).setManager(thirdManager);
                contracts.get(1).setPrice(250000f);
                contracts.get(1).setDate(LocalDate.of(2017, 5, 1));
                contracts.get(1).setClient(new Client("H Group", "Hungary", "Budapest Fiktív utca 82., IV. em./28.2806"));
            }
            contracts.add(new Contract());
            {
                contracts.get(2).setManager(thirdManager);
                contracts.get(2).setPrice(100000f);
                contracts.get(2).setDate(LocalDate.of(2017, 7, 1));
                contracts.get(2).setClient(new Client("I & Sons", "43 Vogel Street Roslyn Palmerston North 4414"));
            }
            contracts.add(new Contract());
            {
                contracts.get(3).setManager(thirdManager);
                contracts.get(3).setPrice(100000f);
                contracts.get(3).setDate(LocalDate.of(2017, 8, 1));
                contracts.get(3).setClient(new Client("J Ent.", "Japan", "Hakusan 4-Chōme 3-2 Bunkyō-ku, TŌKYŌ 112-0001 Japan"));
            }
        }

        thirdManager.setContracts(contracts);

        ArrayList<Manager> managers = new ArrayList<>();
        managers.add(firstManager);
        managers.add(secondManager);
        managers.add(thirdManager);

        return managers;
        //ExEnd:GetManagers
    }

    /// <summary>
    /// Return an array of photo bytes. 
    /// </summary>
    private static byte[] photo() throws Exception
    {
        //ExStart:Photo
        // Load the photo and read all bytes
        byte[] logo = Files.readAllBytes(Paths.get(getImagesDir() + "Logo.jpg"));
        
        return logo;
        //ExEnd:Photo
    }

    /// <summary>
    ///  Return an enumeration of instances of the Contract class.
    /// </summary>
    public static Iterable<Contract> getContracts() throws Exception
    {
        //ExStart:GetContracts
        for (Manager manager : getManagers())
        {
            for (Contract contract : manager.getContracts())
                return (Iterable<Contract>) contract;
        }
        //ExEnd:GetContracts

        return null;
    }
}
