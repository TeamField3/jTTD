package tk.teamfield3.jTTD.display.shader;

import tk.teamfield3.jTTD.display.Material;
import tk.teamfield3.jTTD.util.BufferUtil;
import tk.teamfield3.jTTD.util.math.Matrix4f;
import tk.teamfield3.jTTD.util.math.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private int program;
    private HashMap<String, Integer> uniforms;

    public Shader() {
        program = glCreateProgram();
        uniforms = new HashMap<String, Integer>();

        if (program == 0) {
            System.err.println("Shader creation failed: Could not find valid memory location in constructor");
            System.exit(1);
        }
    }

    public void bind() {
        glUseProgram(program);
    }

    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {

    }

    public void addUniform(String uniform) {
        int uniformLocation = glGetUniformLocation(program, uniform);

        if (uniformLocation == 0xFFFFFFFF) {
            System.err.println("Error: Could not find uniform: " + uniform);
            new Exception().printStackTrace();
            System.exit(1);
        }

        uniforms.put(uniform, uniformLocation);
    }

    public void addVertexShaderFromFile(String text, boolean isInJar) {
        addProgram(loadShader(text, isInJar), GL_VERTEX_SHADER);
    }

//    OpenGL 32
//    public void addGeometryShaderFromFile(String text) {
//        addProgram(loadShader(text), GL_GEOMETRY_SHADER);
//    }

    public void addFragmentShaderFromFile(String text, boolean isInJar) {
        addProgram(loadShader(text, isInJar), GL_FRAGMENT_SHADER);
    }

    public void addVertexShader(String text) {
        addProgram(text, GL_VERTEX_SHADER);
    }

//    OpenGL 32
//    public void addGeometryShader(String text) {
//        addProgram(text, GL_GEOMETRY_SHADER);
//    }

    public void addFragmentShader(String text) {
        addProgram(text, GL_FRAGMENT_SHADER);
    }

    public void compileShader() {
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
            System.err.println(glGetProgramInfoLog(program, 1024));
            System.exit(1);
        }

        glValidateProgram(program);

        if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
            System.err.println(glGetProgramInfoLog(program, 1024));
            System.exit(1);
        }
    }

    private void addProgram(String text, int type) {
        int shader = glCreateShader(type);

        if (shader == 0) {
            System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
            System.exit(1);
        }

        glShaderSource(shader, text);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        glAttachShader(program, shader);
    }

    private static String loadShader(String fileName, boolean isInJar) {
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader = null;

        try {
            if (isInJar)
                shaderReader = new BufferedReader(new InputStreamReader(Shader.class.getResourceAsStream("/shaders/" + fileName)));
            else
                shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));

            String line;

            while ((line = shaderReader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }

            shaderReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }


        return shaderSource.toString();
    }

    public void setUniformi(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniformf(String uniformName, float value) {
        glUniform1f(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, Vector3f value) {
        glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
    }

    public void setUniform(String uniformName, Matrix4f value) {
        glUniformMatrix4(uniforms.get(uniformName), true, BufferUtil.createFlippedBuffer(value));
    }

}
