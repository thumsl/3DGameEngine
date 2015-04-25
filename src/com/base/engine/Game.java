package com.base.engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Game {
	private Mesh mesh;
	private Shader shader;
	private Transform transform;
	
	public Game() {
		mesh = ResourceLoader.loadMesh("box.obj");
		shader = new Shader();
		
//		Vertex[] vertices =  new Vertex[] {new Vertex(new Vector3f(-1, -1, 0)),
//									   new Vertex(new Vector3f(0, 1, 0)),
//									   new Vertex(new Vector3f(1, -1, 0)),
//									   new Vertex(new Vector3f(0, -1, 1))};
//		
//		int[] indices = new int[] {0, 1, 3,
//								   3, 1, 2,
//								   2, 1, 0,
//								   0, 2, 3};
//		
//		mesh.addVertices(vertices, indices);
		
		transform = new Transform();
		transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000f);
		
		shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
		shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
		shader.compileShader();
		
		shader.addUniform("transform");
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
	
	float temp = 0.0f;
	
	public void update() {
		temp += Time.getDelta();
		
		//float sinTemp = (float)Math.sin(temp);
		transform.setTranslation(0, 0, 5);
		//transform.setRotation(0, sinTemp*180, 0);
		//transform.setScale(0.25f, 0.25f, 0.25f);
		//transform.setScale(0.7f * sinTemp, 0.7f * sinTemp, 0.7f * sinTemp);
		
		float xMouse = Input.getMousePosition().getX();
		float yMouse = Input.getMousePosition().getY();
		float yRot = (xMouse / Display.getWidth()) * 360;
		float xRot = (yMouse / Display.getHeight()) * 360;
		transform.setRotation(xRot, yRot, 0);
	}
	
	public void render() {
		shader.bind();
		shader.setUniform("transform", transform.getProjectedTransformation());
		mesh.draw();		
	}
}
