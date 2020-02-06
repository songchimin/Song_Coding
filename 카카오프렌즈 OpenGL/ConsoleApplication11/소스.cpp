#include <windows.h>
#include <MMSystem.h>
#include <gl/glut.h>
#include <math.h>         //�������
#include <stdio.h>
#include <stdlib.h>
#include<cmath>
#define PI 3.1415926
#define rion 1		//ĳ���� �� �߰��ϱ�!!
#define Appeach 2

static int key = 1;
static int song, song2, song3, song4, song5, song6;	//��Ǽ���
static double earth_time = 0;
static double earth_speed = 0;
static double run_time = 0;
static int flag = 0; // ������ ���: w �� c

float	door = 0;
float   radius;
float   hor;
float   ver;
float	camera_x = 0;
float	camera_y = 0;
float	camera_z = 0;

GLUquadricObj* cyl;               //�Ǹ��� ��ü

int a = 0;
int b = 0;
int c = 0;		// ���� ����
float earth_x = 0;
float earth_y = 0;
float earth_z = 0;		//�� ������

int motion = 0;		//����
int view = 0;

float body_x = 0;
float body_y = 0;
float body_z = 0;

float body_angle_x = 0;
float body_angle_y = 0;
float body_angle_z = 90;	//ĳ���� ����

GLfloat body = 0;
GLfloat neck = 0;
GLfloat head = 0;
GLfloat head_back = 0;
GLfloat L_Arm = 0;
GLfloat R_Arm = 0;
GLfloat L_Leg_x = 0;
GLfloat L_Leg_x_y = 0;
GLfloat R_Leg_x = 0;
GLfloat R_Leg_x_y = 0;
GLfloat L_Leg_y = 0;
GLfloat R_Leg_y = 0;
GLfloat body_angle = 0;


void reshape(int w, int h) {
	glViewport(0, 0, w, h);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(60.0, 1.0, 1.0, 100.0);
}

void glInit(void) {
	radius = 2.0;
	hor = 0.0;
	ver = 0.0;
	glEnable(GL_DEPTH_TEST);      //���� �׽���
	glEnable(GL_NORMALIZE);         //����ȭ
	glEnable(GL_SMOOTH);
	glEnable(GL_LIGHTING);

	GLfloat mat_diffuse[] = { 0.5, 0.4, 0.3, 1.0 };		//�л걤
	GLfloat mat_specular[] = { 0.9, 0.9, 0.9, 0.9 };	//�ݻ籤
	GLfloat mat_ambient[] = { 0.5, 0.4, 0.3, 1.0 };		//�ֺ��� ���� ���� (r,g,b,a)
	GLfloat mat_shininess[] = { 80.0 };

	GLfloat light_specular[] = { 1.0, 1.0, 1.0, 1.0 };
	GLfloat light_diffuse[] = { 0.8, 0.8, 0.8, 1.0 };
	GLfloat light_ambient[] = { 0.3, 0.3, 0.3, 1.0 };
	GLfloat light_position[] = { -300.0, 300.0, 1000.0, 0.0 };

	glShadeModel(GL_SMOOTH);
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);

	glLightfv(GL_LIGHT0, GL_POSITION, light_position);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, light_diffuse);
	glLightfv(GL_LIGHT0, GL_SPECULAR, light_specular);
	glLightfv(GL_LIGHT0, GL_AMBIENT, light_ambient);

	glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);
	glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
	glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
	glMaterialfv(GL_FRONT, GL_SHININESS, mat_shininess);
	glEnable(GL_LIGHT0);
	glEnable(GL_COLOR_MATERIAL);
	glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
	glMateriali(GL_FRONT, GL_SHININESS, 128);
	glClearColor(1, 1, 0, 1.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);
}

// ������ ��� ���� �Լ�
void Change_Wire_Or_Solid() {
	if (flag == 0) // �ָ��� ���
		gluQuadricDrawStyle(cyl, GLU_FILL); // �ָ���
	else if (flag == 1) // ���̾� ������ ���
		gluQuadricDrawStyle(cyl, GLU_LINE); // ���̾� ������
}

void DrawEarth(float earth_x, float earth_y, float earth_z) {
	Change_Wire_Or_Solid();
	glPushMatrix();
	glColor3f(0.5, 0.8, 0.0);
	glTranslatef(0.03, -1.7, 0.0);              // ���� ������
	glRotatef(90.0, 0, 1, 0);
	glRotatef(0.0 - earth_time, 0.0, 0.0, 1);         // z���� ���� earth_time��ŭ ȸ��	
	glTranslatef(0, 0, earth_z);			//�� ��ġ �̵�
	glScalef(1, 1, 1.5);
	gluSphere(cyl, 1.2, 100, 100);


	glPushMatrix();						//��
	glColor3f(0.8, 0.65098, 0.23922);
	gluCylinder(cyl, 1.212, 1.172, 0.3, 30, 30);
	glRotatef(180.0, 0, 1, 0);
	gluCylinder(cyl, 1.212, 1.172, 0.3, 30, 30);
	glPopMatrix();

	glPushMatrix();						//������ ��ֹ�
	glColor3f(0.54902, 0.54902, 0.54902);
	glTranslatef(0.0, -1.22, 0.0);              
	gluSphere(cyl, 0.07, 100, 100);
	glPopMatrix();
	


	glPushMatrix();
	glColor3f(0.65098, 0.65098, 0.65098);
	glTranslatef(0.03, 1.5, 0.5);              // ���� ������
	glScalef(0.9, 1, 0.4);
	glutSolidCube(0.9);				//��


	glTranslatef(0, -0.55, 0);
	glScalef(1.5, 0.5, 1.5);
	glutSolidCube(0.7);				//�� ���
	glPopMatrix();


	glPushMatrix();
	glTranslatef(0.03, 1.9, 0.5);              // ���� ������
	glScalef(0.9, 1, 0.4);
	glRotatef(-90.0, 1, 0, 0);
	glColor3f(0.32941, 0.07451, 0);
	glutSolidCone(0.8, 0.6, 20, 50);		//����
	glPopMatrix();
	

	glPushMatrix();
	glRotatef(-30.0, 1, 0, 0);
	glTranslatef(0.03, 1.2, 0.0);          
	glScalef(1.5, 0.2, 0.8);
	glColor3f(0.07059, 0.4, 1.0);
	gluSphere(cyl, 0.3, 100, 100);			//����
	glPopMatrix();



	glPushMatrix();						//��¦
	glColor3f(0.4, 0.29412, 0);
	glTranslatef(0.03 - (door/700), 1.2, 0.315);
	glRotatef(-90.0, 1, 0, 0);
	glRotatef(door, 0, 0, 1);
	glBegin(GL_POLYGON);
	for (int i = 0; i<360; i++) {
		float degInRad = i*(3.14 / 180);
		glVertex3f(cos(degInRad)*(0.25), 0, sin(degInRad)*(0.7));
	}
	glEnd();
	glPopMatrix();


	glPushMatrix();						//â��
	glColor3f(1, 1, 1);
	glScalef(0.5, 1.5, 1);
	glTranslatef(0.8, 1.05, 0.5);
	glutSolidCube(0.25);
	glPopMatrix();

	glPushMatrix();					//âƲ
	glColor3f(0.51765, 0.51765, 0.51765);
	glScalef(0.5, 1.5, 1);
	glTranslatef(0.78, 1.05, 0.5);
	glutSolidCube(0.27);
	glPopMatrix();


	glPushMatrix();					//âƲ
	glColor3f(0.51765, 0.51765, 0.51765);
	glTranslatef(0.46, 1.575, 0.5);
	glScalef(0.05, 1.21, 0.05);
	glutSolidCube(0.31);
	glPopMatrix();

	glPushMatrix();					//âƲ
	glColor3f(0.51765, 0.51765, 0.51765);
	glRotatef(-90.0, 1, 0, 0);
	glTranslatef(0.46, -0.5, 1.575);
	glScalef(0.05, 0.81, 0.05);
	glutSolidCube(0.31);
	glPopMatrix();


	glPopMatrix();

}

void Lion_Drawbody(int x, int a, int b, int c) {
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0, -0.32, 0);
	glRotatef(-90.0, 1.0, 0.0, 0.0);

	glRotatef(x, a, b, c);
	glRotatef(94, 0, 0, 1);

	glTranslatef(body_x, body_y, body_z);
	glRotatef(body_angle, body_angle_x, 0, 0);
	glRotatef(body_angle_y, 0, 1, 0);
	glRotatef(body_angle_z, 0, 0, 1);


	glPushMatrix();
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0, 0, 0.07);
	glScalef(1.0, 0.7, 1.0);
	gluSphere(cyl, 0.095, 50, 50);		//���� ���
	glPopMatrix();

	glPushMatrix();						//�� ������ ��Ʈ
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0, 0, -0.017);
	glScalef(1.0, 0.7, 1.0);
	gluCylinder(cyl, 0.094, 0.0953, 0.097, 30, 30);

	glPushMatrix();
	glTranslatef(0, -0.085, 0.025);
	glRotatef(-5, 1.0, 0, 0);

	glPushMatrix();		//�� �˱׶��
	glColor3f(1, 1, 1);	
	glTranslatef(0, -0.015, 0.02);
	glRotatef(-175, 1.0, 0, 0);
	glBegin(GL_POLYGON);
	for (int i = 0; i<360; i++) {
		float degInRad = i*(3.14 / 180);
		glVertex3f(cos(degInRad)*(0.065), 0, sin(degInRad)*(0.077));
	}
	glEnd();
	glPopMatrix();

	glPushMatrix();						//ƫ�� �ﰢ��
	glTranslatef(0, -0.0275, 0.16);
	glRotatef(-175, 1.0, 0, 0);
	glColor3f(1, 0.607, 0.164);
	glBegin(GL_TRIANGLES);
	glVertex3f(-0.04, 0, 0.065);
	glVertex3f(-0.02, 0, 0.096);
	glVertex3f(0, 0, 0.065);
	glEnd();

	glBegin(GL_TRIANGLES);
	glVertex3f(0, 0, 0.065);
	glVertex3f(0.02, 0, 0.096);
	glVertex3f(0.04, 0, 0.065);
	glEnd();
	glPopMatrix();


	glPopMatrix();
	glPopMatrix();

	glPushMatrix();
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0, 0, -0.03);
	glScalef(1.0, 0.7, 0.7);
	gluSphere(cyl, 0.093, 50, 50);		//���� �ϴ�

	glScalef(1, 1.2, 1.2);
	glTranslatef(0, 0.1, -0.025);
	gluSphere(cyl, 0.03, 50, 50);		//����

	glPopMatrix();
}


void Lion_Drawhead(int x, int a, int b, int c) {
	cyl = gluNewQuadric();
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0, 0, 0.28);
	glRotatef(180, 1, 0, 0);
	glRotatef(x, a, b, c);
	glRotatef(head_back, 0, 0, 1);
	glScalef(1.1, 1, 1.08);						//�Ӹ� Ÿ��
	gluSphere(cyl, 0.15, 50, 50);				//�Ӹ�ũ��

	glPushMatrix();
	glTranslatef(-0.05, 0.086, -0.055);									  
									  
	glPushMatrix();						//������ ����
	glColor3f(0, 0, 0);
	gluCylinder(cyl, 0.05, 0.05, 0.013, 100, 100);
	glPopMatrix();


	glTranslatef(0.1, 0, 0);
	glPushMatrix();
	glColor3f(0, 0, 0);
	gluCylinder(cyl, 0.05, 0.05, 0.013, 100, 100);
	glPopMatrix();

	glPopMatrix();


	glPushMatrix();						//������ ��
	glTranslatef(-0.0275, 0.153, 0.08);
	glRotatef(180, 1, 0, 0);

	glPushMatrix();	
	glColor3f(0, 0, 0);
	glTranslatef(-0.035, 0.0125, 0.09);			//������ġ ���߱�
	gluSphere(cyl, 0.011, 100, 100);
	glPopMatrix();
	glPopMatrix();


	glPushMatrix();						//���� ��
	glTranslatef(0.0275, 0.153, 0.08);
	glRotatef(180, 1, 0, 0);
	glRotatef(180, 0, 0, 1);

	glPushMatrix();
	glColor3f(0.9882, 0.8823, 0.7137);
	glTranslatef(-0.016, -0.02, 0.028);
	glRotatef(-90, 1, 0, 0);
	glPopMatrix();

	glColor3f(0.8, 0.8, 0.8);
	glPushMatrix();
	glTranslatef(-0.012, -0.0125, 0.0245);
	glPopMatrix();

	glPushMatrix();
	glColor3f(0, 0, 0);
	glTranslatef(-0.035, -0.0125, 0.09);
	gluSphere(cyl, 0.011, 100, 100);
	glPopMatrix();
	glPopMatrix();


	glPushMatrix();						//���̾� ��
	glColor3f(0, 0, 0);
	glTranslatef(0, 0.15, 0.025);
	gluSphere(cyl, 0.011, 100, 100);
	glPopMatrix();

	glPushMatrix();
	glColor3f(1, 1, 1);
	glTranslatef(-0.018, 0.13, 0.035);
	gluSphere(cyl, 0.025, 100, 100);
	glPopMatrix();

	glPushMatrix();
	glColor3f(1, 1, 1);
	glTranslatef(0.018, 0.13, 0.035);
	gluSphere(cyl, 0.025, 100, 100);
	glPopMatrix();


	glPushMatrix();					//��
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0.08, -0.01, -0.115);
	glRotatef(130, 0, 1, 0);
	gluSphere(cyl, 0.04, 100, 100);			
	glPopMatrix();

	glPushMatrix();
	glColor3f(1, 0.607, 0.164);
	glTranslatef(-0.08, -0.01, -0.115);
	glRotatef(-130, 0, 1, 0);
	gluSphere(cyl, 0.04, 100, 100);
	glPopMatrix();
}

void Lion_DrawLarm(int x, int a, int b, int c) {			//����
	glColor3f(1, 0.607, 0.164);
	glTranslatef(-0.03, -0.005, 0.129);
	glRotatef(-135.0, 0.0, 1.0, 0.0);
	glRotatef(x, a, b, c);
	glRotatef(-head_back, 1, 0, 0);
	gluCylinder(cyl, 0.033, 0.033, 0.125, 50, 50);

	glPushMatrix();
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0.0, 0.0, 0.12);
	gluSphere(cyl, 0.035, 10, 10);
	glPopMatrix();
}
void Lion_DrawRarm(int x, int a, int b, int c) {			//������
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0.03, -0.005, 0.129);
	glRotatef(135.0, 0.0, 1.0, 0.0);
	glRotatef(x, a, b, c);
	gluCylinder(cyl, 0.033, 0.033, 0.125, 50, 50);

	glPushMatrix();
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0.0, 0.0, 0.12);
	gluSphere(cyl, 0.035, 10, 10);
	glPopMatrix();
}

void Lion_DrawLleg_x(int x, int a, int b, int c) {
	glColor3f(1, 0.607, 0.164);
	glTranslatef(-0.04, 0.0, 0.02);
	glRotatef(180, 1.0, 0.0, 0.0);
	glRotatef(x, a, b, c);
	glRotatef(L_Leg_x_y, 0, 1, 0);
	gluCylinder(cyl, 0.022, 0.0220, 0.11, 50, 50);		//���� ����

	glPushMatrix();					
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0.0, 0.0, 0.1);
	gluSphere(cyl, 0.024, 10, 10);
	glPopMatrix();
}
void Lion_DrawLleg_y(int y, int a, int b, int c) {
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0.0, 0.0, 0.09);
	glRotatef(y, a, b, c);
	gluCylinder(cyl, 0.036, 0.036, 0.07, 10, 10);		//���� ��

	glPushMatrix();			
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0.0, 0.0, 0.075);
	gluSphere(cyl, 0.036, 10, 10);
	glPopMatrix();
}
void Lion_DrawRleg_x(int x, int a, int b, int c) {
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0.04, 0.0, 0.02);
	glRotatef(180, 1.0, 0.0, 0.0);
	glRotatef(x, a, b, c);
	glRotatef(R_Leg_x_y, 0, 1, 0);
	gluCylinder(cyl, 0.022, 0.0220, 0.11, 50, 50);

	glPushMatrix();	//������ ����
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0.0, 0.0, 0.1);
	gluSphere(cyl, 0.024, 10, 10);
	glPopMatrix();
}
void Lion_DrawRleg_y(int y, int a, int b, int c) {
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0.0, 0.0, 0.09);
	glRotatef(y, a, b, c);
	gluCylinder(cyl, 0.036, 0.036, 0.07, 10, 10);

	glPushMatrix();		//������ ��
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0.0, 0.0, 0.075);
	gluSphere(cyl, 0.036, 10, 10);
	glPopMatrix();

}


//����ġ
void Appeach_Drawbody(int x, int a, int b, int c) {
	glColor3f(1, 0.607, 0.164);
	glTranslatef(0, -0.32, 0);
	glRotatef(-90.0, 1.0, 0.0, 0.0);

	glRotatef(x, a, b, c);
	glRotatef(94, 0, 0, 1);

	glTranslatef(body_x, body_y, body_z);
	glRotatef(body_angle, body_angle_x, 0, 0);
	glRotatef(body_angle_y, 0, 1, 0);
	glRotatef(body_angle_z, 0, 0, 1);



	glPushMatrix();			//�� ��ܺ�
	glColor3f(1, 1, 1);
	glTranslatef(0, 0, 0.07);
	glScalef(1.0, 0.9, 1.0);
	gluSphere(cyl, 0.095, 50, 50);
	glPopMatrix();

	glPushMatrix();		//��Ʈ
	glColor3f(1, 1, 1);
	glTranslatef(0, 0, -0.017);
	glScalef(1.0, 0.9, 1.0);
	gluCylinder(cyl, 0.094, 0.0953, 0.097, 30, 30);
	glPopMatrix();

	glPushMatrix();
	glColor3f(1, 1, 1);
	glTranslatef(0, 0, -0.03);
	glScalef(1.0, 0.9, 0.7);
	gluSphere(cyl, 0.093, 50, 50);
	glPopMatrix();
}

void Appeach_Drawhead(int x, int a, int b, int c) {
	cyl = gluNewQuadric();
	glColor3f(0.95686, 0.66667, 0.67451);
	glTranslatef(0, 0, 0.28);
	glRotatef(180, 1, 0, 0);
	glRotatef(x, a, b, c);
	glRotatef(head_back, 0, 0, 1);
	glScalef(1.3, 0.9, 1.1);						//�Ӹ� Ÿ��
	gluSphere(cyl, 0.15, 50, 50);				//�Ӹ�ũ��


	glPushMatrix();
	glColor3f(0.95686, 0.66667, 0.67451);
	glTranslatef(0.01, 0.02, -0.088);
	glRotatef(180, 1, 0, 0);
	glScalef(1.08, 1, 1);
	glutSolidCone(0.096, 0.11, 20, 50);


	glPopMatrix();


	glPushMatrix();		//����ġ ������
	glColor3f(1.0, 0.4, 0.4);
	glRotatef(-90, 0, 1, 0);
	glTranslatef(0.015, 0.145, -0.07);
	glBegin(GL_POLYGON);
	for (int i = 0; i<360; i++) {
		float degInRad = i*(3.14 / 180);
		glVertex3f(cos(degInRad)*(0.012), 0, sin(degInRad)*(0.03));
	}
	glEnd();
	glPopMatrix();

	glPushMatrix();		//����ġ
	glColor3f(1.0, 0.4, 0.4);
	glRotatef(-90, 0, 1, 0);
	glTranslatef(0.015, 0.14, 0.08);
	glBegin(GL_POLYGON);
	for (int i = 0; i<360; i++) {
		float degInRad = i*(3.14 / 180);
		glVertex3f(cos(degInRad)*(0.012), 0, sin(degInRad)*(0.03));
	}

	glEnd();
	glPopMatrix();

	glPushMatrix();
	glColor3f(0.9882, 0.8823, 0.7137);
	glTranslatef(-0.04, 0.096, -0.03);
	
	glPushMatrix();
	glColor3f(1, 1, 1);
	glScalef(1.8, 1.2, 0.9);
	gluSphere(cyl, 0.04, 100, 100);			//������ ����

	glColor3f(0, 0, 0);
	glPointSize(30.0f);
	glBegin(GL_LINES);
	glVertex3f(-0.025, 0.005, -0.02);
	glVertex3f(-0.03, 0.01, -0.055);
	glEnd();

	glTranslatef(0.01, 0.008, 0.0);
	glBegin(GL_LINES);
	glVertex3f(-0.025, 0.007, -0.02);
	glVertex3f(-0.025, 0.01, -0.065);
	glEnd();

	glTranslatef(0.01, 0.008, 0.0);
	glBegin(GL_LINES);
	glVertex3f(-0.025, 0.007, -0.02);
	glVertex3f(-0.02, 0.01, -0.06);
	glEnd();

	glColor3f(0, 0, 0);
	glTranslatef(-0.0338, 0.003, -0.00075);
	glRotatef(36, 0, 0, 1);
	glRotatef(-7, 1.8, 1.3, -0.1);
	//glRotatef(1.5, 1, 0, 1);
	glScalef(0.93, 0.3, 1.03);
	gluSphere(cyl, 0.035, 100, 100);		//�� ���̶���

	glPopMatrix();

	glPushMatrix();
	glColor3f(1, 1, 1);
	glScalef(1.8, 1.2, 0.9);
	glTranslatef(0.04, 0.0033, 0.0);
	gluSphere(cyl, 0.04, 100, 100);			//���� ����


	glColor3f(0, 0, 0);
	glPointSize(30.0f);
	glBegin(GL_LINES);
	glVertex3f(-0.005, 0.03, -0.02);			//�Ӵ���
	glVertex3f(-0.0055, 0.035, -0.05);
	glEnd();

	glTranslatef(0.01, 0.008, 0.0);
	glBegin(GL_LINES);
	glVertex3f(-0.002, 0.015, -0.03);
	glVertex3f(-0.0025, 0.025, -0.06);
	glEnd();

	glTranslatef(0.01, 0.008, 0.0);
	glBegin(GL_LINES);
	glVertex3f(0.0, -0.0001, -0.03);
	glVertex3f(0.003, 0.005, -0.06);
	glEnd();


	glColor3f(0, 0, 0);
	glTranslatef(-0.0062, 0.0025, -0.0007);
	glRotatef(-36, 0, 0, 1);
	glRotatef(-7, 1.8, 1.3, -0.2);
	glScalef(0.96, 0.3, 1.08);
	gluSphere(cyl, 0.034, 100, 100);		//�� ���̶���

	glPopMatrix();
	glPopMatrix();


	glPushMatrix();						//������ ��
	glTranslatef(-0.0275, 0.153, 0.05);
	glRotatef(180, 1, 0, 0);

	glPushMatrix();
	glColor3f(0, 0, 0);
	glTranslatef(-0.025, 0.014, 0.085);			//������ġ ���߱�
	gluSphere(cyl, 0.01, 100, 100);
	glPopMatrix();

	glPushMatrix();
	glColor3f(0, 0, 0);
	glTranslatef(0.095, 0.015, 0.085);			//������ġ ���߱�
	gluSphere(cyl, 0.01, 100, 100);
	glPopMatrix();
	glPopMatrix();

	//��
	glPushMatrix();
	glColor3f(0, 0, 0);
	glTranslatef(0, 0.14, 0.02);
	glRotatef(65, 1, 0, 0);
	glRotatef(10, 0, 1, 0);
	gluCylinder(cyl, 0.03, 0.03, 0.06, 30, 30);
	glPopMatrix();
}

void Appeach_DrawLarm(int x, int a, int b, int c) {			//����
	glColor3f(1, 1, 1);
	glTranslatef(-0.03, -0.005, 0.129);
	glRotatef(-135.0, 0.0, 1.0, 0.0);
	glRotatef(x, a, b, c);
	glRotatef(-head_back, 1, 0, 0);
	gluCylinder(cyl, 0.033, 0.033, 0.125, 50, 50);
	glPushMatrix();
	glColor3f(1, 1, 1);
	glTranslatef(0.0, 0.0, 0.12);
	gluSphere(cyl, 0.035, 10, 10);
	glPopMatrix();
}
void Appeach_DrawRarm(int x, int a, int b, int c) {			//������
	glColor3f(1, 1, 1);
	glTranslatef(0.03, -0.005, 0.129);
	glRotatef(135.0, 0.0, 1.0, 0.0);
	glRotatef(x, a, b, c);
	gluCylinder(cyl, 0.033, 0.033, 0.125, 50, 50);

	glPushMatrix();
	glColor3f(1, 1, 1);
	glTranslatef(0.0, 0.0, 0.12);
	gluSphere(cyl, 0.035, 10, 10);
	glPopMatrix();
}

void Appeach_DrawLleg_x(int x, int a, int b, int c) {
	glColor3f(1, 1, 1);
	glTranslatef(-0.04, 0.0, 0.02);
	glRotatef(180, 1.0, 0.0, 0.0);
	glRotatef(x, a, b, c);
	glRotatef(L_Leg_x_y, 0, 1, 0);
	gluCylinder(cyl, 0.022, 0.0220, 0.11, 50, 50);


	glPushMatrix();	//���� ����
	glColor3f(1, 1, 1);
	glTranslatef(0.0, 0.0, 0.1);
	gluSphere(cyl, 0.024, 10, 10);
	glPopMatrix();
}
void Appeach_DrawLleg_y(int y, int a, int b, int c) {
	glColor3f(1, 1, 1);
	glTranslatef(0.0, 0.0, 0.09);
	glRotatef(y, a, b, c);
	gluCylinder(cyl, 0.036, 0.036, 0.07, 10, 10);

	glPushMatrix();			//���� ��
	glColor3f(1, 1, 1);
	glTranslatef(0.0, 0.0, 0.075);
	gluSphere(cyl, 0.036, 10, 10);
	glPopMatrix();
}
void Appeach_DrawRleg_x(int x, int a, int b, int c) {
	glColor3f(1, 1, 1);
	glTranslatef(0.04, 0.0, 0.02);
	glRotatef(180, 1.0, 0.0, 0.0);
	glRotatef(x, a, b, c);
	glRotatef(R_Leg_x_y, 0, 1, 0);
	gluCylinder(cyl, 0.022, 0.0220, 0.11, 50, 50);

	glPushMatrix();	//������ ����
	glColor3f(1, 1, 1);
	glTranslatef(0.0, 0.0, 0.1);
	gluSphere(cyl, 0.024, 10, 10);
	glPopMatrix();
}
void Appeach_DrawRleg_y(int y, int a, int b, int c) {
	glColor3f(1, 1, 1);
	glTranslatef(0.0, 0.0, 0.09);
	glRotatef(y, a, b, c);
	gluCylinder(cyl, 0.036, 0.036, 0.07, 10, 10);

	glPushMatrix();		//������ ��
	glColor3f(1, 1, 1);
	glTranslatef(0.0, 0.0, 0.075);
	gluSphere(cyl, 0.036, 10, 10);
	glPopMatrix();
}



void character() {
	
	if (motion == 1)
	{
		if (song3 == 1)						//���� ���θ���
		{
			if (body_z > -0.05) {
				body_z -= 0.01;
			}
			else {
				body_z = -0.05;
			}

			if (L_Leg_x <= -40) {
				L_Leg_x = -40;;
			}
			else {
				L_Leg_x -= 7;
			}
			if (L_Leg_y >= 60) {
				L_Leg_y = 60;
			}
			else {
				L_Leg_y += 10;
			}


			if (R_Leg_x <= -40) {
				R_Leg_x = -40;;
			}
			else {
				R_Leg_x -= 7;
			}
			if (R_Leg_y >= 60) {
				R_Leg_y = 60;
				song3 = 2;
			}
			else {
				R_Leg_y += 10;
			}

			if (L_Arm >= 20) {
				L_Arm = 20;
			}
			else {
				L_Arm += 3;
			}
			if (R_Arm >= 20) {
				R_Arm = 20;
			}
			else {
				R_Arm += 3;
			}
		}
		else if (song3 == 2)
		{
			if (body_z < 0.15) {
				body_z += 0.03;
			}
			else {
				body_z = 0.15;
			}


			if (L_Leg_x >= 0) {
				L_Leg_x = 0;;
			}
			else {
				L_Leg_x += 7;
			}
			if (L_Leg_y <= 0) {
				L_Leg_y = 0;
				song3 = 3;
			}
			else {
				L_Leg_y -= 10;
			}

			if (L_Arm <= 0) {
				L_Arm = 0;
			}
			else {
				L_Arm -= 3;
			}
			if (R_Arm <= 0) {
				R_Arm = 0;
			}
			else {
				R_Arm -= 3;
			}
		}
		else if (song3 == 3)
		{
			if (body_z > 0) {
				body_z -= 0.01;
			}
			else {
				body_z = 0;
			}

			if (R_Leg_x >= 0) {
				R_Leg_x = 0;;
			}
			else {
				R_Leg_x += 7;
			}
			if (R_Leg_y <= 0) {
				R_Leg_y = 0;
			}
			else {
				R_Leg_y -= 10;
			}
			
		}
	}

	if (motion == 3) {				// ����
		body_angle_z = 0;

		if (song4 == 1)
		{
			if (door > 130)
			{
				door = 130;
				song4 = 2;
			}
			else
				door += 1.85;

		}
		else if (song4 = 2)
		{
			L_Arm = sin(run_time) * 60;
			R_Arm = -L_Arm;
			L_Leg_x = sin(run_time) * 40;
			L_Leg_y = abs(-sin(run_time) * 20 - 50);
			R_Leg_x = -L_Leg_x;
			R_Leg_y = abs(sin(run_time) * 20 - 50);

			if (body_y < -0.7) {
				body_y = -0.705;
				L_Arm = 0;
				R_Arm = -L_Arm;
				L_Leg_x = 0;
				L_Leg_y = 0;
				R_Leg_x = -L_Leg_x;
				R_Leg_y = 0;
				body_angle_z = 90;
				view = 1;
				if (song4 = 3)
				{
					if (door <= 0)
					{
						door = 0;
						song4 = 0;
					}
					else
						door -= 2;
				}
			}
			else {
				body_y -= 0.01;
			}
		}
	}

	if (motion == 4) {

		L_Arm = sin(run_time) * 60;				
		R_Arm = -L_Arm;						
		L_Leg_x = sin(run_time) * 40;
		//L_Leg_y = abs(-sin(run_time) * 20 - 50);
		R_Leg_x = -L_Leg_x;
		//R_Leg_y = abs(sin(run_time) * 20 - 50);	

		body_angle_z = 90;

		if (body_x >= 1) {
			body_x = 0.0;
			L_Arm = 0;
			R_Arm = -L_Arm;
			L_Leg_x = 0;
			L_Leg_y = 0;
			R_Leg_x = -L_Leg_x;
			R_Leg_y = 0;
			body_angle_z = 90;
			earth_speed = 0;
		}
	}


	if (motion == 5) {
			body_x = 0.0;
			L_Arm = 0;
			R_Arm = -L_Arm;
			L_Leg_x = 0;
			L_Leg_y = 0;
			R_Leg_x = -L_Leg_x;
			R_Leg_y = 0;
			body_angle_z = 90;
			earth_speed = 0;
		}


	if (motion == 7) {					// �����Ϸ� ����

		if (song6 == 1)
		{
			L_Arm = sin(run_time) * 60;
			R_Arm = -L_Arm;
			L_Leg_x = sin(run_time) * 40;
			L_Leg_y = abs(-sin(run_time) * 20 - 50);
			R_Leg_x = -L_Leg_x;
			R_Leg_y = abs(sin(run_time) * 20 - 50);

			body_angle_z = 180;

			if (body_y >= 0.3) {
				body_y = 0.3;
				L_Arm = 0;
				R_Arm = -L_Arm;
				L_Leg_x = 0;
				L_Leg_y = 0;
				R_Leg_x = -L_Leg_x;
				R_Leg_y = 0;
				song6 = 2;
			}
			else {
				body_y += 0.01;
			}
		}
		else if (song6 == 2)
		{
			if (L_Arm > 75) {
				L_Arm = 80;
			}
			else {
				L_Arm += 5;
			}
			if (R_Arm > 75) {
				R_Arm = 800;
			}
			else {
				R_Arm += 5;
			}

			glPushMatrix();
			glColor3f(1, 1, 1);
			glTranslatef(-0.7, 0.1, 0.0);		//���ô�
			glRotatef(90, 0, 1, 0);
			glRotatef(45, 1, 0, 0);
			gluCylinder(cyl, 0.01, 0.01, 0.4, 10, 10);

			glRotatef(-30, 1, 0, 0);
			glColor3f(0, 0, 0);
			glPointSize(100.0f);
			glBegin(GL_LINES);
			glVertex3f(0.0, 0.0, 0.0);			//������
			glVertex3f(0.0, -0.8, 0.0);
			glEnd();
			glPopMatrix();
		}
		
	}


	if (motion == 8) {					//�� ������ �ƿ�!
		if (song5 == 1)
		{
			if (door > 130)
			{
				door = 130;
				song5 = 2;
			}
			else
				door += 1.85;

		}
		else if (song5 = 2)
		{
			L_Arm = sin(run_time) * 60;
			R_Arm = -L_Arm;
			L_Leg_x = sin(run_time) * 40;
			L_Leg_y = abs(-sin(run_time) * 20 - 50);
			R_Leg_x = -L_Leg_x;
			R_Leg_y = abs(sin(run_time) * 20 - 50);

			body_angle_z = 180;

			if (body_y >= 0) {
				body_y = 0;
				L_Arm = 0;
				R_Arm = -L_Arm;
				L_Leg_x = 0;
				L_Leg_y = 0;
				R_Leg_x = -L_Leg_x;
				R_Leg_y = 0;
				body_angle_z = 90;
				view = 1;
				song5 = 3;
				if (song5 == 3)
				{
					if (door <= 0)
					{
						door = 0;
					}
					else
						door -= 2;
				}
			}
			else {
				body_y += 0.01;
			}
		}
	}


	if (motion == 9) {
		if (song == 1)						//���� ���θ���
		{
			if (body_z >= -0.2) {
				body_z -= 0.01;
			}
			else {
				body_z = -0.2;
			}

			if (L_Leg_x <= -60) {
				L_Leg_x = -60;;
			}
			else {
				L_Leg_x -= 5;
			}
			if (L_Leg_y >= 90) {
				L_Leg_y = 90;
			}
			else {
				L_Leg_y += 10;
			}


			if (R_Leg_x <= -60) {
				R_Leg_x = -60;;
			}
			else {
				R_Leg_x -= 5;
			}
			if (R_Leg_y >= 90) {
				R_Leg_y = 90;
				song = 2;
			}
			else {
				R_Leg_y += 10;
			}

			if (L_Arm >= 20) {
				L_Arm = 20;
			}
			else {
				L_Arm += 3;
			}
			if (R_Arm >= 20) {
				R_Arm = 20;
			}
			else {
				R_Arm += 3;
			}
		}
		
		if (song == 2) {					//�ɾƼ� �� �ٸ� ���
			if (L_Leg_y <= -30) {
				L_Leg_y = -30;
			}
			else {
				L_Leg_y -= 5;
			}

			if (R_Leg_y <= -30) {
				R_Leg_y = -30;
			}
			else {
				R_Leg_y -= 5;
			}
		}
	
	}

	if (motion == 10) {			//�Ͼ��̤�

		if (song2 == 1)				//���� ����
		{
			if (L_Leg_y >= 90) {
				L_Leg_y = 90;
			}
			else {
				L_Leg_y += 5;
			}
			if (R_Leg_y >= 90) {
				R_Leg_y = 90;
				song2 = 2;
			}
			else {
				R_Leg_y += 5;
			}
	
		}

		else if (song2 == 2)		//�Ͼ�鼭 �ٸ� ���
		{
			if (body_z < 0.0) {
				body_z += 0.01;
			}
			else {
				body_z = 0.0;
			}

			if (L_Leg_x < 0) {
				L_Leg_x += 5;
			}
			else {
				L_Leg_x = 0;
			}

			if (R_Leg_x < 0) {
				R_Leg_x += 5;
			}
			else {
				R_Leg_x = 0;
			}


			if (L_Leg_y > 0) {
				L_Leg_y -= 10;
			}
			else {
				L_Leg_y = 0;
			}
			if (R_Leg_y > 0) {
				R_Leg_y -= 10;

			}
			else {
				R_Leg_y = 0;
			}

			if (L_Arm <= 0) {
				L_Arm = 0;
			}
			else {
				L_Arm-= 3;
			}
			if (R_Arm <= 0) {
				R_Arm = 0;
			}
			else {
				R_Arm -= 3;
			}

		}
	
	}



if (key == rion)		//���̾�
{
	glPushMatrix();
	Lion_Drawbody(body, 1, 0, 0);		//����
	glPushMatrix();
	Lion_Drawhead(head, 0, 1, 0);				//�Ӹ�
	glPopMatrix();

	glPushMatrix();
	Lion_DrawLarm(L_Arm, 1, 0, 0);				//����
	glPopMatrix();

	glPushMatrix();
	Lion_DrawRarm(R_Arm, 1, 0, 0);				//������
	glPopMatrix();

	glPushMatrix();
	Lion_DrawLleg_x(L_Leg_x, 1, 0, 0);			//�������
	glPushMatrix();
	Lion_DrawLleg_y(L_Leg_y, 1, 0, 0);		//�� ���Ƹ�
	glPopMatrix();
	glPopMatrix();

	glPushMatrix();
	Lion_DrawRleg_x(R_Leg_x, 1, 0, 0);			//���� �����
	glPushMatrix();
	Lion_DrawRleg_y(R_Leg_y, 1, 0, 0);		//���� ���Ƹ�
	glPopMatrix();
	glPopMatrix();
	glPopMatrix();
	}
	

if (key == Appeach)	//����ġ
{
	glPushMatrix();
	Appeach_Drawbody(body, 1, 0, 0);		//����
	glPushMatrix();
	Appeach_Drawhead(head, 0, 1, 0);				//�Ӹ�
	glPopMatrix();

	glPushMatrix();
	Appeach_DrawLarm(L_Arm, 1, 0, 0);				//����
	glPopMatrix();

	glPushMatrix();
	Appeach_DrawRarm(R_Arm, 1, 0, 0);				//������
	glPopMatrix();

	glPushMatrix();
	Appeach_DrawLleg_x(L_Leg_x, 1, 0, 0);			//�������
	glPushMatrix();
	Appeach_DrawLleg_y(L_Leg_y, 1, 0, 0);		//�� ���Ƹ�
	glPopMatrix();
	glPopMatrix();

	glPushMatrix();
	Appeach_DrawRleg_x(R_Leg_x, 1, 0, 0);			//���� �����
	glPushMatrix();
	Appeach_DrawRleg_y(R_Leg_y, 1, 0, 0);		//���� ���Ƹ�
	glPopMatrix();
	glPopMatrix();
	glPopMatrix();
}
}

void Run() {
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // �ʱ�ȭ
	glMatrixMode(GL_MODELVIEW);               // ��� ����
	glLoadIdentity();                        // CTM �ʱ�ȭ
	cyl = gluNewQuadric();                     // �Ǹ��� ��ü ����

	camera_x = radius * cos(ver) * cos(hor);
	camera_y = radius * cos(ver) * sin(hor);
	camera_z = radius * sin(ver);

	if (view == 0) {
		if (motion == 3 || motion == 8) {
			gluLookAt(1.5, 1.5, 0.7, 0.0, 0.0, 0, 0.0, 0.0, 1.0);
		}
		else {
			gluLookAt(camera_x, camera_y, camera_z, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0);
		}
	}
	else if (view == 1) {
		gluLookAt(camera_x, camera_y, camera_z, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0);
	}


	glRotatef(90.0, 1.0, 0.0, 0.0);
	glRotatef(-90.0, 0.0, 1.0, 0.0);
	character();							// ĳ����
	DrawEarth(earth_x, earth_y, earth_z);	// ��
	glLoadIdentity();                       // �ʱ�ȭ
	glutSwapBuffers();
}

void MyDisplay() {
	Run();
}

void MyTimer(int Value) {
	earth_time = earth_time + earth_speed;	// �� ȸ�� ����
	run_time = run_time + 0.1;
	glutPostRedisplay();
	glutTimerFunc(40, MyTimer, 1);
}


void Key(unsigned char key, int x, int y) {
	switch (key) {
	case 'j':case 'J':
		motion = 1;
		song3 = 1;
		break;
	case 'i':case 'L':			//�� ���� in
		motion = 3;
		song4 = 1;
		break;
	case 'o':case 'O':			//�� ������ out
		song5 = 1;
		motion = 8;
		break;
	case 'f':case 'F':			//�������� ����
		motion = 7;
		song6 = 1;
		break;
	case 'g':					//�� ȸ�� go
		motion = 4;
		earth_speed = -0.5;
		break;
	case 's':				//���߱� stop
		motion = 5;
		break;
	case 'd': case 'D':		//�ɱ� down
		motion = 9;
		song = 1;
		break;
	case 'u': case 'P':		//�Ͼ�� up
		motion = 10;
		song2 = 1;
		break;
	case '-': 
		radius += 0.1;	//ī�޶� �ޱ�
		break;
	case '+': 
		radius -= 0.1;
		break;
	case 'v': case 'V':
		if (view == 1) 
			view = 0;	
		else if (view == 0) 
			view = 1;
		break;

	case 'c': //�տ� s����ؼ� c�� ��ü
		flag = 0;
		break;
	case 'w': // ������ ���: ���̾� ������ //����earth�� ����
		flag = 1;
		break;

	case 'q':			//����
		exit(0);
	default:
		break;
	}
}

void SpecialKey(int key, int x, int y) {
	switch (key) {						//����Ű ������ȯ
	case GLUT_KEY_LEFT:
		hor -= 0.2;
		break;
	case GLUT_KEY_RIGHT:
		hor += 0.2;
		break;
	case GLUT_KEY_UP:
		ver += 0.2;
		break;
	case GLUT_KEY_DOWN:
		ver -= 0.2;
		break;
	}
}

void MyKeyboard(unsigned char KeyPressed, int x, int y) {
	switch (KeyPressed) {

	}
}

void MyMainMenu(int entryID) {
	key = entryID;
}


int main(int argc, char** argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_RGB | GLUT_DOUBLE | GLUT_DEPTH);
	glutInitWindowSize(750, 750);
	glutInitWindowPosition(0, 0);
	glutCreateWindow("Kakao friends town");
	glInit();

	GLint MyMainMenuID = glutCreateMenu(MyMainMenu); // �޴� ���� �� �ݹ� �Լ� ����
	glutAddMenuEntry("���̾�", 1);
	glutAddMenuEntry("����ġ", 2);
	glutAttachMenu(GLUT_RIGHT_BUTTON); // �޴� ����: ���콺 ���� ��ư


	glutTimerFunc(40, MyTimer, 1);
	glutReshapeFunc(reshape);
	glutSpecialFunc(SpecialKey);
	glutKeyboardFunc(Key);
	glutDisplayFunc(MyDisplay);
	glutMainLoop();
	return 0;
}
