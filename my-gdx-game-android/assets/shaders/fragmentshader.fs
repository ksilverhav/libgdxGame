#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec3 v_playerPos;
uniform sampler2D u_texture;

void main() {
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
	gl_FragColor = gl_FragColor * vec4(1+v_playerPos.x/1920,1+v_playerPos.y/1080,1,1);
}