package com.base.engine;

public class Game {
	private Mesh mesh;
	private Shader shader;
	private Material material;
	private Transform transform;
	private Camera camera;
	
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	public Game() {
		mesh = new Mesh(); // ResourceLoader.loadMesh("box.obj");
		material = new Material(ResourceLoader.loadTexture("wood.png"), new Vector3f(1, 1, 0));
		shader = PhongShader.getInstance();
		camera = new Camera();
		transform = new Transform();
		
		Vertex[] vertices = new Vertex[] { new Vertex( new Vector3f(-1.0f, -1.0f, 0.5773f),	new Vector2f(0.0f, 0.0f)),
		        new Vertex( new Vector3f(0.0f, -1.0f, -1.15475f),		new Vector2f(0.5f, 0.0f)),
		        new Vertex( new Vector3f(1.0f, -1.0f, 0.5773f),		new Vector2f(1.0f, 0.0f)),
		        new Vertex( new Vector3f(0.0f, 1.0f, 0.0f),      new Vector2f(0.5f, 1.0f)) };

				int indices[] = { 0, 3, 1,
				1, 3, 2,
				2, 3, 0,
				1, 2, 0 };
		
		mesh.addVertices(vertices, indices, true);
		
		transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.01f, 1000f);
		transform.setCamera(camera);
		
		PhongShader.setAmbientLight(new Vector3f(0.1f, 0.1f, 0.1f));
		PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight (new Vector3f(1,1,1), 0.8f), new Vector3f(1,1,1)));
	}
	
	public void input() {
		camera.input();
		
		if (Input.getKey(Input.KEY_LEFT))
			left = true;
		else
			left = false;
		
		if (Input.getKeyDown(Input.KEY_RIGHT))
			right = true;
		if (Input.getKeyUp(Input.KEY_RIGHT))
			right = false;
		
		if (Input.getKeyDown(Input.KEY_UP))
			up = true;
		if (Input.getKeyUp(Input.KEY_UP))
			up = false;		
		
		if (Input.getKeyDown(Input.KEY_DOWN))
			down = true;
		if (Input.getKeyUp(Input.KEY_DOWN))
			down = false;
	}
	
	float temp = 0.0f;
	float xRot = 0.0f;
	float yRot = 0.0f;
	
	public void update() {
		temp += Time.getDelta();
		float movAmt = (float) (10 * Time.getDelta());
		
		//float sinTemp = (float)Math.sin(temp);
		transform.setTranslation(0, 0, 5);
		//transform.setRotation(0, sinTemp*180, 0);
		//transform.setScale(0.25f, 0.25f, 0.25f);
		//transform.setScale(0.7f * sinTemp, 0.7f * sinTemp, 0.7f * sinTemp);
		
		// Keyboard Movement
		if (left)
			xRot -= 100 * Time.getDelta();
		if (right)
			xRot += 100 * Time.getDelta();
		if (up)
			yRot += 100 * Time.getDelta();
		if (down)
			yRot -= 100 * Time.getDelta();
		
		if(Input.getKey(Input.KEY_W))
			camera.move(camera.getForward(), movAmt);
		if(Input.getKey(Input.KEY_S))
			camera.move(camera.getForward(), -movAmt);
		if(Input.getKey(Input.KEY_A))
			camera.move(camera.getLeft(), movAmt);
		if(Input.getKey(Input.KEY_D))
			camera.move(camera.getRight(), movAmt);
		
		transform.setRotation(yRot, xRot, 0);
	}
	
	public void render() {
		RenderUtil.setClearColor(new Vector3f(0,0,0));
		shader.bind();
		shader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), material);
		mesh.draw();		
	}
}
