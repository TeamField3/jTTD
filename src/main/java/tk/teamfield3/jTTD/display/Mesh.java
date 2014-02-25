package tk.teamfield3.jTTD.display;

import tk.teamfield3.jTTD.util.BufferUtil;
import tk.teamfield3.jTTD.util.math.Vector2f;
import tk.teamfield3.jTTD.util.math.Vector3f;

import java.io.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh {

    private int vbo;
    private int ibo;
    private int size;

    public Mesh(String filePath) {
        initMeshData();
        loadMesh(filePath);
    }

    private void initMeshData() {
        vbo = glGenBuffers();
        ibo = glGenBuffers();
        size = 0;
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

    private Mesh loadMesh(String filePath) {
        String[] splitArray = filePath.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        if (!ext.equals("obj")) {
            System.err.println("Error: only obj files are supported");
            new Exception().printStackTrace();
            System.exit(1);
        }

        try {
            BufferedReader meshReader = new BufferedReader(new FileReader(new File(filePath)));
            loadOBJ(meshReader);
            return this;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Mesh loadMesh(String fileName, Object classInJar) {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        if (!ext.equals("obj")) {
            System.err.println("Error: only obj files are supported");
            new Exception().printStackTrace();
            System.exit(1);
        }

        InputStream stream = classInJar.getClass().getResourceAsStream(fileName);

        BufferedReader meshReader = new BufferedReader(new InputStreamReader(stream));
        loadOBJ(meshReader);

        return this;
    }

    private void addVertices(Vertex[] vertices, int[] indices) {
        size = indices.length;

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtil.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtil.createFlippedBuffer(indices), GL_STATIC_DRAW);
    }

    private void loadOBJ(BufferedReader reader) {
        try {
            ArrayList<Vector3f> vertexCoords = new ArrayList<Vector3f>();
            ArrayList<Integer> indices = new ArrayList<Integer>();
            ArrayList<Vector2f> textureCoords = new ArrayList<Vector2f>();
            ArrayList<Vector3f> vertexNorms = new ArrayList<Vector3f>();
            String line;
            while ((line = reader.readLine()) != null) {
                //Indicates a vertex
                if (line.startsWith("v ")) {
                    float x = Float.valueOf(line.split(" ")[1]);
                    float y = Float.valueOf(line.split(" ")[2]);
                    float z = Float.valueOf(line.split(" ")[3]);
                    vertexCoords.add(new Vector3f(x, y, z));
                }
                //Indicates a vertex normal
                else if (line.startsWith("vn ")) {
                    float x = Float.valueOf(line.split(" ")[1]);
                    float y = Float.valueOf(line.split(" ")[2]);
                    float z = Float.valueOf(line.split(" ")[3]);
                    vertexNorms.add(new Vector3f(x, y, z));
                }
                //Indicates a texture coordinate
                else if (line.startsWith("vt ")) {
                    float x = Float.valueOf(line.split(" ")[1]);
                    float y = Float.valueOf(line.split(" ")[2]);
                    textureCoords.add(new Vector2f(x, y));
                }
                //Indicates a face
                else if (line.startsWith("f ")) {
                    indices.add(Integer.parseInt(line.split(" ")[1].split("/")[0]) - 1);
                    indices.add(Integer.parseInt(line.split(" ")[2].split("/")[0]) - 1);
                    indices.add(Integer.parseInt(line.split(" ")[3].split("/")[0]) - 1);

                    if (line.split(" ").length > 4) {
                        indices.add(Integer.parseInt(line.split(" ")[1].split("/")[0]) - 1);
                        indices.add(Integer.parseInt(line.split(" ")[3].split("/")[0]) - 1);
                        indices.add(Integer.parseInt(line.split(" ")[4].split("/")[0]) - 1);
                    }
                }
            }
            reader.close();

            if (textureCoords.size() == vertexCoords.size() && vertexNorms.size() == vertexCoords.size()) {
                ArrayList<Vertex> vertices = new ArrayList<Vertex>();
                for (Vector3f vertex : vertexCoords) {
                    for (Vector2f texture : textureCoords) {
                        for (Vector3f norms : vertexNorms) {
                            vertices.add(new Vertex(vertex, texture, norms));
                        }
                    }
                }

                Vertex[] vertexData = new Vertex[vertices.size()];
                vertices.toArray(vertexData);

                Integer[] indexData = new Integer[indices.size()];
                indices.toArray(indexData);

                addVertices(vertexData, BufferUtil.toIntArray(indexData));
            } else if (textureCoords.size() == vertexCoords.size()) {
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

                addVertices(vertexData, BufferUtil.toIntArray(indexData));
            } else if (vertexNorms.size() == vertexCoords.size()) {
                ArrayList<Vertex> vertices = new ArrayList<Vertex>();
                for (Vector3f vertex : vertexCoords) {
                    for (Vector3f normal : vertexNorms) {
                        vertices.add(new Vertex(vertex, normal));
                    }
                }

                Vertex[] vertexData = new Vertex[vertices.size()];
                vertices.toArray(vertexData);

                Integer[] indexData = new Integer[indices.size()];
                indices.toArray(indexData);

                addVertices(vertexData, BufferUtil.toIntArray(indexData));
            } else {
                ArrayList<Vertex> vertices = new ArrayList<Vertex>();
                for (Vector3f vertex : vertexCoords) {
                    vertices.add(new Vertex(vertex));
                }

                Vertex[] vertexData = new Vertex[vertices.size()];
                vertices.toArray(vertexData);

                Integer[] indexData = new Integer[indices.size()];
                indices.toArray(indexData);

                addVertices(vertexData, BufferUtil.toIntArray(indexData));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
