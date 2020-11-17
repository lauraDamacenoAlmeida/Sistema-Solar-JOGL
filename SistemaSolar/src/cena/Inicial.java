//Laura Damaceno de Almeida RA:20964736
//Gabriel Oliveira Ramos do Nascimento RA: 21022939
package cena;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT; //primitivas 3D
import java.awt.Color;
import java.awt.Font;
import com.jogamp.opengl.util.awt.TextRenderer;

/**
 *
 * @author siabr
 */
public class Inicial implements GLEventListener {

    public float angulo = 0;
    public float anguloLuz = 60f;
    private GL2 gl;
    private GLU glu;
    private GLUT glut;
    private TextRenderer textRenderer;
    public int tonalizacao = GL2.GL_SMOOTH;
    public boolean liga = true;
    public int larguraFrame;
    public int alturaFrame;
    public boolean T, t, E, e, r;

    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        GL2 gl = drawable.getGL().getGL2();
        textRenderer = new TextRenderer(new Font("Comic Sans MS Negrito", Font.PLAIN, 38));

        //habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        //obtem o contexto Opengl
        gl = drawable.getGL().getGL2();
        glut = new GLUT(); //objeto da biblioteca glut

        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);
        //limpa a janela com a cor especificada
        //limpa o buffer de profundidade
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); //lê a matriz identidade
        desenhaTexto(gl, 0, 570, Color.WHITE, "Rotação da Tera em torno do Sol");
        if (liga) {
            iluminacaoAmbiente(gl);
            ligaLuz(gl);
        }

        desenhaSol(drawable, gl, glut);
        desenhaTerra(drawable, gl, glut);

        if (liga) {
            desligaluz(gl);
        }
        desenhaTexto(gl, 0, 0, Color.WHITE, "Aperte R para rotacionar");

        gl.glFlush();
    }

    public void desenhaSol(GLAutoDrawable drawable, GL2 gl, GLUT glut) {
        gl.glPushMatrix();
        gl.glColor3f(1, 1, 0);
        glut.glutSolidSphere(60, 100, 10);
        gl.glPopMatrix();
    }

    public void desenhaTerra(GLAutoDrawable drawable, GL2 gl, GLUT glut) {
        gl.glPushMatrix();
        gl.glColor3f(0, 0, 1);
        gl.glRotatef(angulo, 0, 1, 0); // rotação orientada ao eixo Y e Z
        gl.glTranslatef(-80f, 0f, -8);
        glut.glutSolidSphere(10, 100, 10);
        gl.glPopMatrix();

    }

    public void desenhaTexto(GL2 gl, int x, int y, Color cor, String frase) {
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(cor);

        textRenderer.draw(frase, x, y);
        textRenderer.endRendering();

    }

    public void iluminacaoAmbiente(GL2 gl) {
        float luzAmbiente[] = {0.2f, 0.2f, 0.2f, 1.0f}; //cor
        float posicaoLuz[] = {anguloLuz, 0.0f, 100.0f, 0f}; //infinito

        // define parametros de luz de número 0 (zero)
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);
    }

    public void ligaLuz(GL2 gl) {
        // habilita a definição da cor do material a partir da cor corrente
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        // habilita o uso da iluminação na cena
        gl.glEnable(GL2.GL_LIGHTING);
        // habilita a luz de número 0
        gl.glEnable(GL2.GL_LIGHT0);
        //Especifica o Modelo de tonalizacao a ser utilizado 
        //GL_FLAT -> modelo de tonalizacao flat 
        //GL_SMOOTH -> modelo de tonalização GOURAUD (default)        
        gl.glShadeModel(tonalizacao);
    }

    public void desligaluz(GL2 gl) {
        //desabilita o ponto de luz
        gl.glDisable(GL2.GL_LIGHT0);
        //desliga a iluminacao
        gl.glDisable(GL2.GL_LIGHTING);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //obtem o contexto grafico Opengl
        gl = drawable.getGL().getGL2();
        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); //lê a matriz identidade
        //projeção ortogonal (xMin, xMax, yMin, yMax, zMin, zMax)
        gl.glOrtho(-100, 100, -100, 100, -100, 100);
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        System.out.println("Reshape: " + width + ", " + height);

        larguraFrame = width;
        alturaFrame = height;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

}
