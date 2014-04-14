#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec3 v_playerPos;
uniform sampler2D u_texture;
float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}
void main() {
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
	//if(abs(gl_FragCoord.x/1920 - v_playerPos.x/1920) < 0.1 && abs(gl_FragCoord.y/1080 - v_playerPos.y/1080) < 0.1)
		//gl_FragColor = vec4(gl_FragColor.rgb*(1-abs(gl_FragCoord.x/1920 - v_playerPos.x/1920)),gl_FragColor.a);
		//sqrt(pow(abs(gl_FragCoord.x/1920 - v_playerPos.x/1920),2) + pow(abs(gl_FragCoord.y/1080 - v_playerPos.y/1080),2))
		
		
		//if((1-sqrt(pow(abs(gl_FragCoord.x/1280 - v_playerPos.x/1920),2) + pow(abs(gl_FragCoord.y/720 - v_playerPos.y/1080),2))) < 0.7)
		//{
		//gl_FragColor = vec4(gl_FragColor.rgb*(1-sqrt(pow(abs(gl_FragCoord.x/1920 - v_playerPos.x/1920),2) + pow(abs(gl_FragCoord.y/1080 - v_playerPos.y/1080),2))),gl_FragColor.a);
		//gl_FragColor = gl_FragColor*gl_FragColor;
		//}
		vec4 color = texture2D(u_texture, v_texCoords);
		vec2 pos = vec2(v_playerPos.x/1920 - gl_FragCoord.x/1280, v_playerPos.y/1080 - gl_FragCoord.y/720);
		float len = length(pos);
		float vignette = smoothstep(.5, .4, len*(1 + rand(v_playerPos.xy)));
		color.rgb = mix(color.rgb, color.rgb * vignette, .7);
		gl_FragColor = color;
		
	
	
	
	
}