package tk.teamfield3.jTTD.display;

import tk.teamfield3.jTTD.util.BufferUtil;
import tk.teamfield3.jTTD.util.math.Vector2f;
import tk.teamfield3.jTTD.util.math.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh {

    private int vbo;
    private int ibo;
    private int size;

    public Mesh(String fileName) {
        initMeshData();
        loadMesh(fileName);
    }

    public Mesh(Vertex[] vertices, int[] indices) {
        this(vertices, indices, false);
    }

    public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals) {
        initMeshData();
        addVertices(vertices, indices, calcNormals);
    }

    private void initMeshData() {
        vbo = glGenBuffers();
        ibo = glGenBuffers();
        size = 0;
    }

    private void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals) {
        if (calcNormals) {
            calcNormals(vertices, indices);
        }

        size = indices.length;

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtil.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtil.createFlippedBuffer(indices), GL_STATIC_DRAW);
    }

    public void draw() {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
    }

    private void calcNormals(Vertex[] vertices, int[] indices) {
        for (int i = 0; i < indices.length; i += 3) {
            int i0 = indices[i];
            int i1 = indices[i + 1];
            int i2 = indices[i + 2];

            Vector3f v1 = vertices[i1].getPos().subtract(vertices[i0].getPos());
            Vector3f v2 = vertices[i2].getPos().subtract(vertices[i0].getPos());

            Vector3f normal = v1.cross(v2).getNormalized();

            vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
            vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
            vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
        }

        for (int i = 0; i < vertices.length; i++)
            vertices[i].setNormal(vertices[i].getNormal().getNormalized());
    }

    private Mesh loadMesh(String fileName) {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        if (!ext.equals("obj")) {
            System.err.println("Error: only obj files are supported");
            new Exception().printStackTrace();
            System.exit(1);
        }

        ArrayList<Vector3f> vertexCoords = new ArrayList<Vector3f>();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        ArrayList<Vector2f> textureCoords = new ArrayList<Vector2f>();

        BufferedReader meshReader = null;

        try {
            meshReader = new BufferedReader(new FileReader("./res/models/" + fileName));
            String line;

            while ((line = meshReader.readLine()) != null) {
                String[] tokens = line.split(" ");
                tokens = BufferUtil.removeEmptyStrings(tokens);

                if (tokens.length == 0 || tokens[0].equals("#"))
                    continue;
                else if (tokens[0].equals("v")) {
                    vertexCoords.add(new Vector3f(Float.valueOf(tokens[1]),
                            Float.valueOf(tokens[2]),
                            Float.valueOf(tokens[3])));
                } else if (tokens[0].equals("f")) {
                    indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
                    indices.add(Integer.parseInt(tokens[2].split("/")[0]) - 1);
                    indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);

                    if (tokens.length > 4) {
                        indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
                        indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
                        indices.add(Integer.parseInt(tokens[4].split("/")[0]) - 1);
                    }
                } else if (tokens[0].equals("vt")) {
                    textureCoords.add(new Vector2f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2])));
                }
            }

            meshReader.close();

            if (textureCoords.size() == vertexCoords.size()) {
                ArrayList<Vertex> vertices = new ArrayList<Vertex>();
                for (Vector3f vertex : vertexCoords) {
                    for (Vector2f texture : textureCoords) {
                        vertices.add(new Vertex(vertex, texture));
                    }
                }

                Vertex[] vertexData = new Vertex[vertices.size()];
                vertices.toArray(vertexData);

                Integer[] indexData = new Integer[indices.size()];
                indices.toArray(indexData);

                addVertices(vertexData, BufferUtil.toIntArray(indexData), true);
            } else {
                ArrayList<Vertex> vertices = new ArrayList<Vertex>();
                for (Vector3f vertex : vertexCoords) {
                    vertices.add(new Vertex(vertex));
                }
                Vertex[] vertexData = new Vertex[vertexCoords.size()];
                vertices.toArray(vertexData);

                Integer[] indexData = new Integer[indices.size()];
                indices.toArray(indexData);

                addVertices(vertexData, BufferUtil.toIntArray(indexData), true);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

}
