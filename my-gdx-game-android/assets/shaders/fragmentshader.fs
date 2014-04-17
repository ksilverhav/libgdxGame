#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec3 v_playerPos;
varying vec2 v_resolution;

uniform sampler2D u_texture;
uniform sampler2D u_shadowmap;

uniform vec2      tcOffset[25]; // Texture coordinate offsets
float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}



void main() {
    	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
		
		vec4 color = texture2D(u_texture, v_texCoords);
		//vec4 color = texture2D(u_shadowMap, vec2(0,0));
		vec2 pos = vec2(gl_FragCoord.x/v_resolution.x, gl_FragCoord.y/v_resolution.y);

		gl_FragColor = color;	
}
