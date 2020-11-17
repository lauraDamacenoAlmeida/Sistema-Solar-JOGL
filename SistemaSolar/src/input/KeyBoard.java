//Laura Damaceno de Almeida RA:20964736
//Gabriel Oliveira Ramos do Nascimento RA: 21022939

package input;

import cena.Inicial;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;

/**
 *
 * @author Siabreu
 */
public class KeyBoard implements KeyListener {

    private Inicial inicial;

    public KeyBoard(Inicial inicial) {
        this.inicial = inicial;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

        switch (e.getKeyChar()) {
            case 'r':
                inicial.angulo += 20.0f;
                //incremento utilizado para dar movimento a iluminação
                inicial.anguloLuz += 10.f;
                //if para delimitar o máximo que a iluminação pode percorrer no eixo X
                if (inicial.anguloLuz >= 110f) {
                    inicial.anguloLuz = -100f;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
