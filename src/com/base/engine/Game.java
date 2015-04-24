package com.base.engine;

import org.lwjgl.input.Keyboard;

public class Game {
	private Mesh mesh;
	private Shader shader;
	
	public Game() {
		mesh = new Mesh();
		shader = new Shader();
		
		Vertex[] data =  new Vertex[] {new Vertex(new Vector3f(-1, -1, 0)),
									   new Vertex(new Vector3f(0, 1, 0)),
									   new Vertex(new Vector3f(1, -1, 0))};
		
		mesh.addVertices(data);
		
		shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
		shader.addVertexShader(ResourceLoader.loadShader("basicFragment.fs"));
		shader.compileShader();
	}
	
	public void input() {
		if (Input.getKeyDown(Keyboard.KEY_UP))
			System.out.println("We've just pressed up!");
		if (Input.getKeyUp(Keyboard.KEY_UP))
			System.out.println("We've just released up!");
		
		if (Input.getMouseDown(0))
			System.out.println("We've just right clicked at " + Input.getMousePosition());
		if (Input.getMouseUp(0))
			System.out.println("We've just released right mouse button!");
	}
	
	public void update() {
		
	}
	
	public void render() {
		shader.bind();
		mesh.draw();		
	}
}
