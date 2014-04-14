attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;
uniform vec3 u_playerPos;

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec3 v_playerPos;

void main() {
    v_color = a_color;
    v_texCoords = a_texCoord0;
	v_playerPos = u_playerPos;
    gl_Position = u_projTrans * a_position;
}