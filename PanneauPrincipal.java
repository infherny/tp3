import javax.swing.*;
import java.awt.*;

/**
 * Panneau principal
 */
public class PanneauPrincipal extends JPanel{

    BdQuestionsReponses bd;
    JPanel panneauPrincipal;
    JPanel panneauDeGauche;
    PanneauOuiNon panneauCentre;
    JPanel panneauBas;

    public PanneauPrincipal() {

        panneauPrincipal = new JPanel(new BoxLayout(panneauPrincipal, BoxLayout.Y_AXIS));
    }


    public void initComposants() {

        panneauPrincipal.add(panneauDeGauche = new JPanel(), FlowLayout.LEFT);
        panneauPrincipal.add(panneauBas = new JPanel(), FlowLayout.TRAILING);
    }

    public void creerPanCentral() {

        if (panneauCentre != null)
            panneauPrincipal.remove(panneauCentre);

        panneauCentre.add(bd.reponseTrouvee()?
                (panneauCentre = new PanneauReponse(bd, panneauCentre))
                :(panneauCentre = new PanneauReponse(bd, panneauCentre)));

        panneauCentre.validate();
    }
}
