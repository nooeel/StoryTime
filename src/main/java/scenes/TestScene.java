package scenes;

import engine.Camera;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import renderer.Shader;
import util.Time;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class TestScene extends Scene {

    private int vertexID, fragmentID, shaderProgramm;

    private float[] vertexArray = {
            // pos                  //color
             100.0f, 0.0f, 0.0f,     1.0f, 0.0f, 0.0f, 1.0f,     // bottom right 0
            0.0f,  100.0f, 0.0f,     0.0f, 1.0f, 0.0f, 1.0f,     // Top Left     1
            100.0f,  100.0f, 0.0f,     0.0f, 0.0f, 1.0f, 1.0f,     // Top Right    2
            0.0f, 0.0f, 0.0f,     1.0f, 1.0f, 0.0f, 1.0f,     // bottom left  3
    };

    // WICHTIG: Immer gegen den Uhrzeigersinn
    private int[] elementArray = {

            2, 1, 0,    // Top right triangle
            0, 1, 3     // bottom left triangle
    };

    private int vaoID, vboID, eboID;

    private Shader defaultShader;

    public TestScene() {


    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());

        System.out.println("In TestScene - 0");


        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();

        // ===============================================================
        // Generate VAO,VBO, EBO buffer objs, and send to GPU
        // ===============================================================
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // create VBO upload vertex buff
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // create indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // add vertex attribute pointers
        int positionSize = 3;
        int colorSize = 4;
        int floatSizeInBytes = 4;
        int vertexSizeInBytes = (positionSize + colorSize) * floatSizeInBytes;

        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeInBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeInBytes,
                positionSize * floatSizeInBytes);
        glEnableVertexAttribArray(1);

    }

    @Override
    public void update(float dt) {

        camera.position.x -= dt * 50.0f;

        defaultShader.use();
        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());

        defaultShader.uploadFloat("uTime", Time.getTime());


        // Bind the VAO that were using
        glBindVertexArray(vaoID);

        // Enable vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // unbind everything
        glBindVertexArray(0);


        defaultShader.detach();
    }
}
