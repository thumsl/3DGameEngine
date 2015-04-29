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
	
	PointLight pLight1 = new PointLight(new BaseLight(new Vector3f(1, 0, 0), 0.8f), new Attenuation(0, 0, 1), new Vector3f(-2, 0, 5f));
	PointLight pLight2 = new PointLight(new BaseLight(new Vector3f(0, 0, 1), 0.8f), new Attenuation(0, 0, 1), new Vector3f(2, 0, 7f));
	
	public Game() {
		mesh =  new Mesh(); //ResourceLoader.loadMesh("box.obj");
		material = new Material(ResourceLoader.loadTexture("grid.png"), new Vector3f(1, 1, 1), 1, 8);
		shader = PhongShader.getInstance();
		camera = new Camera();
		transform = new Transform();
		
//		Vertex[] vertices = new Vertex[] { new Vertex( new Vector3f(-1.0f, -1.0f, 0.5773f),	new Vector2f(0.0f, 0.0f)),
//		        new Vertex( new Vector3f(0.0f, -1.0f, -1.15475f),		new Vector2f(0.5f, 0.0f)),
//		        new Vertex( new Vector3f(1.0f, -1.0f, 0.5773f),		new Vector2f(1.0f, 0.0f)),
//		        new Vertex( new Vector3f(0.0f, 1.0f, 0.0f),      new Vector2f(0.5f, 1.0f)) };
//
//				int indices[] = { 0, 3, 1,
//				1, 3, 2,
//				2, 3, 0,
//				1, 2, 0 };
//		Pyramid ^
		
		float fieldDepth = 20.0f;
		float fieldWidth = 20.0f;
		
		Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
											new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 10f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(10f, 0.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(10f, 10f))};
		
		int indices[] = { 0, 1, 2, 2, 1, 3};
		// Plane ^
		
		mesh.addVertices(vertices, indices, true);
		
		Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.01f, 1000f);
		Transform.setCamera(camera);
		
		PhongShader.setAmbientLight(new Vector3f(0.1f, 0.1f, 0.1f));
		PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight (new Vector3f(1,0.6f,0.1f), 0.8f), new Vector3f(0,-0.1f,0)));

		PhongShader.setPointLights(new PointLight[] {pLight1, pLight2});
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
		
		float sinTemp = (float)Math.sin(temp);
		float cosTemp = (float)Math.cos(temp);
		
		transform.setTranslation(0, -1, 5);
		
		pLight1.setPosition(new Vector3f(3,0,8.0f * (float)(Math.sin(temp) + 1.0/2.0) + 10));
		pLight2.setPosition(new Vector3f(7,0,8.0f * (float)(Math.cos(temp) + 1.0/2.0) + 10));
		
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
