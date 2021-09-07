E="(x*x+y*y)/10";
E=inline(E);
y=1;
h=.1;
x0=0;
xn=.4;

printf('x \t y \t');
printf('\n');
printf('%f %f \n',x0,y);

for x=x0:h:xn-h
  x=x+h;
  y=y+E(x,y)*h;
  printf('%f %f \n',x,y);
endfor
