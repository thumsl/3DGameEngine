package com.base.engine;

public class Game {
	private Mesh mesh;
	private Shader shader;
	private Transform transform;
	private Texture texture;
	private Camera camera;
	
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	public Game() {
		mesh = new Mesh(); // ResourceLoader.loadMesh("box.obj");
		texture = ResourceLoader.loadTexture("wood.png");
		shader = new Shader();
		camera = new Camera();
		
		Vertex[] vertices =  new Vertex[] {new Vertex(new Vector3f(-1, -1, 0), new Vector2f(0, 0)),
									   new Vertex(new Vector3f(0, 1, 0), new Vector2f(0.5f, 0)),
									   new Vertex(new Vector3f(1, -1, 0), new Vector2f(1, 0)),
									   new Vertex(new Vector3f(0, -1, 1), new Vector2f(0, 0.5f))};
		
		int[] indices = new int[] {3, 1, 0,
								   2, 1, 3,
								   0, 1, 2,
								   0, 2, 3};
		
		mesh.addVertices(vertices, indices);
		
		transform = new Transform();
		transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.01f, 1000f);
		transform.setCamera(camera);
		
		shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
		shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
		shader.compileShader();
		
		shader.addUniform("transform");
	}
	
	public void input() {
		camera.input();
		
		if (Input.getKeyDown(Input.KEY_A))
			left = true;
		if (Input.getKeyUp(Input.KEY_A))
			left = false;
		
		if (Input.getKeyDown(Input.KEY_D))
			right = true;
		if (Input.getKeyUp(Input.KEY_D))
			right = false;
		
		if (Input.getKeyDown(Input.KEY_W))
			up = true;
		if (Input.getKeyUp(Input.KEY_W))
			up = false;		
		
		if (Input.getKeyDown(Input.KEY_S))
			down = true;
		if (Input.getKeyUp(Input.KEY_S))
			down = false;
	}
	
	float temp = 0.0f;
	float xRot = 0.0f;
	float yRot = 0.0f;
	
	public void update() {
		temp += Time.getDelta();
		
		//float sinTemp = (float)Math.sin(temp);
		transform.setTranslation(0, 0, 5);
		//transform.setRotation(0, sinTemp*180, 0);
		//transform.setScale(0.25f, 0.25f, 0.25f);
		//transform.setScale(0.7f * sinTemp, 0.7f * sinTemp, 0.7f * sinTemp);
		
		// Mouse movement
		//float xMouse = Input.getMousePosition().getX();
		//float yMouse = Input.getMousePosition().getY();
		//float yRot = (xMouse / Display.getWidth()) * 360;
		//float xRot = (yMouse / Display.getHeight()) * 360;
		
		// Keyboard Movement
		if (left)
			xRot -= 100 * Time.getDelta();
		if (right)
			xRot += 100 * Time.getDelta();
		if (up)
			yRot += 100 * Time.getDelta();
		if (down)
			yRot -= 100 * Time.getDelta();
		
		transform.setRotation(yRot, xRot, 0);
	}
	
	public void render() {
		shader.bind();
		shader.setUniform("transform", transform.getProjectedTransformation());
		texture.bind();
		mesh.draw();		
	}
}
