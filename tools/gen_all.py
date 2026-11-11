"""Genera WheelchairModel.java, BikeModel.java, texturas de entidad (silla + 16 bicis) e iconos de item de las bicis.
Uso (desde la raiz del proyecto):  python3 tools/gen_all.py      (necesita Pillow)"""
import math, os
from PIL import Image, ImageDraw
JAVA='src/main/java/com/silla/'; RES='src/main/resources/assets/silla/textures/'
GRAY=(172,174,180); WHITE=(238,239,243); DARK=(44,44,50); MID=(78,80,88); TIRE=(30,30,34)
SPOKE=(150,152,160); HUB=(205,205,212); CHROME=(196,198,206); RIMC=(120,124,134); BLACK=(24,24,28)
DYES=[('white',(236,236,236)),('orange',(240,118,19)),('magenta',(189,68,179)),('light_blue',(58,175,217)),
 ('yellow',(254,216,61)),('lime',(112,185,25)),('pink',(237,141,172)),('gray',(84,90,94)),
 ('light_gray',(154,154,148)),('cyan',(21,137,145)),('purple',(121,42,172)),('blue',(53,57,157)),
 ('brown',(114,71,40)),('green',(84,109,27)),('red',(205,28,28)),('black',(34,36,42))]
KEEP={'white','orange','light_blue','yellow','lime','pink','cyan','purple','blue','red','black'}
DYES=[d for d in DYES if d[0] in KEEP]
def f(v):
    s=('%.4f'%v).rstrip('0').rstrip('.'); return s+'F'
def sz(d): return (2*math.ceil(d[5])+2*math.ceil(d[3]), math.ceil(d[5])+math.ceil(d[4]))
def pack(keys,TW,TH):
    items=sorted(keys.items(),key=lambda kv:-sz(kv[1][0])[1]); x=y=rowh=0; uv={}
    for k,(d,c) in items:
        w,h=sz(d)
        if x+w>TW: x=0; y+=rowh; rowh=0
        uv[k]=(x,y); x+=w; rowh=max(rowh,h)
    assert y+rowh<=TH,(y+rowh,TH); return uv
def shade(c,fa): return tuple(max(0,min(255,int(v*fa))) for v in c)+(255,)
def paint(keys,uv,TW,TH,frame=None):
    img=Image.new('RGBA',(TW,TH),(0,0,0,0))
    def fill(r,col):
        x0,y0,w,h=r
        for i in range(math.floor(x0),math.ceil(x0+w)):
            for j in range(math.floor(y0),math.ceil(y0+h)):
                if 0<=i<TW and 0<=j<TH: img.putpixel((i,j),col)
    for k,(d,c) in keys.items():
        col=frame if c=='FRAME' else c; u,v=uv[k]; w,h,dd=d[3],d[4],d[5]
        fill((u+dd,v,w,dd),shade(col,1.12)); fill((u+dd+w,v,w,dd),shade(col,0.68))
        fill((u,v+dd,dd,h),shade(col,0.9)); fill((u+dd,v+dd,w,h),shade(col,1.0))
        fill((u+dd+w,v+dd,dd,h),shade(col,0.9)); fill((u+2*dd+w,v+dd,w,h),shade(col,0.82))
    return img
def cub(uv,k,d): u,v=uv[k]; return f'.uv({u}, {v}).cuboid({", ".join(f(t) for t in d)})'

def ring(prefix,key,dims,y,angles,parent,uv,out,ox=0.0):
    """Anillo poligonal: una caja por lado, girada alrededor del eje de la rueda."""
    for i,a in enumerate(angles):
        out.append(f'        {parent}.addChild("{prefix}{i}", ModelPartBuilder.create(){cub(uv,key,dims)}, ModelTransform.rotation({f(math.radians(a))}, 0F, 0F));')

HEAD='''package com.silla;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

/** %s (generado por tools/gen_all.py). */
public class %s extends EntityModel<%s> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(Identifier.of("silla", "%s"), "main");
    private final ModelPart root;
    private final ModelPart wheelA, wheelB, wheelC, wheelD;
    /** Giro de las ruedas (radianes). Lo fija el renderer antes de dibujar. */
    public float roll;

    public %s(ModelPart root) {
        this.root = root;
        this.wheelA = root.getChild("%s");
        this.wheelB = root.getChild("%s");
        this.wheelC = root.getChild("%s");
        this.wheelD = root.getChild("%s");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
'''
def tail(tw,th,setang):
    return f'''
        return TexturedModelData.of(data, {tw}, {th});
    }}

    @Override
    public void setAngles(%s entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {{
{setang}
    }}

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {{
        root.render(matrices, vertices, light, overlay, color);
    }}
}}
'''

# ============ SILLA DE RUEDAS ============
keys={}; fixed=[]
def b(n,x,y,z,w,h,d,c): keys['bb_'+n]=((x,y,z,w,h,d),c); fixed.append(('bb_'+n,(x,y,z,w,h,d)))
b('asiento_base',-6,-11,-5,12,1,11,GRAY); b('asiento_cojin',-5.5,-12,-4.5,11,1,9,WHITE)
b('respaldo_marco',-6,-26,4.5,12,14,1.5,GRAY); b('respaldo_cojin',-5,-24,3.5,10,11,1,WHITE)
for s,x in (('izq',-7.5),('der',6)):
    b('poste_'+s,x,-30,5,1.5,19,1.5,GRAY); b('mango_'+s,x,-31,6.5,1.5,1.5,5,DARK)
for s,x in (('izq',-8),('der',6)):
    b('apoyabrazos_'+s,x,-19,-4,2,1.5,10,GRAY); b('soporte_brazo_'+s,x+0.25,-17.5,-4,1.5,8,1.5,GRAY)
for s,x in (('izq',-7.5),('der',6)):
    b('riel_'+s,x,-10,-5.5,1.5,1.5,12,GRAY); b('placa_rueda_'+s,x,-12,0.5,1.5,8,3,GRAY)
b('travesano',-6,-8.5,-3,12,1,1.5,MID); b('eje',-8,-7.5,1.4,16,1,1.2,DARK)
for s,x in (('izq',-7.25),('der',5.75)): b('pata_del_'+s,x,-9,-8.75,1.5,6.5,1.5,GRAY)
for s,x in (('izq',-7),('der',2)): b('reposapies_'+s,x,-3.5,-12,5,1,5,DARK)
# rueda mejorada: neumatico redondo (16 lados), aro metalico, 4 rayos dobles, cubo, aro de empuje
keys['w_tire']=((-1.3,-7,-1.4,2.6,1.7,2.9),TIRE)       # lado del neumatico (apotema 7)
keys['w_rim']=((-1.6,-5.3,-1.0,3.2,0.8,2.1),RIMC)      # aro interior (apotema 5.3, 16 lados)
keys['w_spoke']=((-0.45,-5.0,-0.3,0.9,10.0,0.6),SPOKE)
keys['w_hub']=((-1.8,-1.8,-1.8,3.6,3.6,3.6),HUB)
keys['w_push_l']=((-3.4,-5.9,-1.1,0.8,0.7,2.4),CHROME)  # aro de empuje (lado exterior izq)
keys['w_push_r']=((2.6,-5.9,-1.1,0.8,0.7,2.4),CHROME)
keys['c_tire']=((-1,-3.0,-1.0,2,0.7,2.1),TIRE); keys['c_hub']=((-0.9,-0.9,-0.9,1.8,1.8,1.8),HUB)
keys['c_body']=((-0.9,-2.3,-1.0,1.8,0.9,2.0),RIMC)
uv=pack(keys,128,128); paint(keys,uv,128,128).save(RES+'entity/silla_de_ruedas.png')
a16=[i*22.5 for i in range(16)]; a8=[i*22.5 for i in range(8)]; a4=[0,45,90,135]
o=[HEAD%('Modelo de la silla de ruedas','WheelchairModel','WheelchairEntity','silla_de_ruedas','WheelchairModel','rueda_izq','rueda_der','ruedita_izq','ruedita_der')]
o.append('        root.addChild("bb_main", ModelPartBuilder.create()')
o.append('\n'.join('                '+cub(uv,k,d) for k,d in fixed)+',\n                ModelTransform.pivot(0F, 24F, 0F));')
for side,px,pk in (('izq',-9,'w_push_l'),('der',9,'w_push_r')):
    o.append(f'        ModelPartData rueda_{side} = root.addChild("rueda_{side}", ModelPartBuilder.create(), ModelTransform.pivot({f(px)}, 17F, 2F));')
    ring('tire','w_tire',keys['w_tire'][0],0,a16,f'rueda_{side}',uv,o)
    ring('rim','w_rim',keys['w_rim'][0],0,a16,f'rueda_{side}',uv,o)
    ring('push','%s'%pk,keys[pk][0],0,a16,f'rueda_{side}',uv,o)
    ring('spoke','w_spoke',keys['w_spoke'][0],0,[0,45,90,135],f'rueda_{side}',uv,o)
    o.append(f'        rueda_{side}.addChild("cubo", ModelPartBuilder.create(){cub(uv,"w_hub",keys["w_hub"][0])}, ModelTransform.NONE);')
for side,px in (('izq',-6.5),('der',6.5)):
    o.append(f'        ModelPartData ruedita_{side} = root.addChild("ruedita_{side}", ModelPartBuilder.create(), ModelTransform.pivot({f(px)}, 21.5F, -8F));')
    ring('t','c_tire',keys['c_tire'][0],0,[i*45 for i in range(8)],f'ruedita_{side}',uv,o)
    o.append(f'        ruedita_{side}.addChild("cubo", ModelPartBuilder.create(){cub(uv,"c_hub",keys["c_hub"][0])}, ModelTransform.NONE);')
o.append(tail(128,128,'        wheelA.pitch = roll;\n        wheelB.pitch = roll;\n        wheelC.pitch = roll * 2.8F; // ruedas chicas: radio 2.5 vs 7\n        wheelD.pitch = roll * 2.8F;')%'WheelchairEntity')
open(JAVA+'WheelchairModel.java','w').write('\n'.join(o))

# ============ BICICLETA ============
keys={}; fixed=[]
def bb(n,x,y,z,w,h,d,c): keys['bb_'+n]=((x,y,z,w,h,d),c); fixed.append(('bb_'+n,(x,y,z,w,h,d)))
bb('tubo_asiento',-0.75,-15,-1.75,1.5,9,1.5,'FRAME')
bb('tubo_direccion',-0.75,-16,-8.75,1.5,9,1.5,'FRAME')
bb('tubo_superior',-0.75,-13.5,-8.5,1.5,1.5,8.5,'FRAME')
bb('tubo_inferior',-0.75,-8.5,-8,1.5,1.5,7.5,'FRAME')
bb('vaina_izq',-1.6,-7,-1,0.8,1,13,'FRAME'); bb('vaina_der',0.8,-7,-1,0.8,1,13,'FRAME')
bb('horquilla_izq',-1.9,-10,-9.6,0.8,5,1,CHROME); bb('horquilla_der',1.1,-10,-9.6,0.8,5,1,CHROME)
bb('potencia',-0.5,-18,-8.75,1,2,1,DARK); bb('manillar',-5,-18.5,-9,10,1,1,DARK)
bb('puno_izq',-6.2,-19,-9.5,1.7,1.8,2,BLACK); bb('puno_der',4.5,-19,-9.5,1.7,1.8,2,BLACK)
bb('tija',-0.4,-16.5,-1.4,0.8,2,0.8,CHROME); bb('sillin',-1.8,-17.2,-4,3.6,1.2,6,BLACK)
bb('parrilla',-2.5,-12,5,5,0.8,6.5,DARK); bb('parrilla_poste',-0.4,-11.2,6.5,0.8,5,0.8,DARK)
bb('pedal_izq',-3.6,-9.4,-2.1,2,0.7,2.2,BLACK); bb('pedal_der',1.6,-3.6,-2.1,2,0.7,2.2,BLACK)
bb('biela_izq',-2.0,-9.0,-1.3,0.7,3.0,0.8,CHROME); bb('biela_der',1.3,-6.0,-1.3,0.7,3.0,0.8,CHROME)
bb('guardabarro',-1.2,-13,-13.5,2.4,0.8,5,'FRAME')
keys['t_tire']=((-0.8,-6,-1.2,1.6,0.9,2.5),TIRE); keys['t_spoke']=((-0.25,-5.1,-0.2,0.5,10.2,0.4),SPOKE)
keys['t_hub']=((-1.0,-1.0,-1.0,2,2,2),CHROME)
keys['stay']=((-0.4,0,-0.4,0.8,14.8,0.8),'FRAME')
uv=pack(keys,128,128)
paint(keys,uv,128,128,frame=(200,28,28)).save('/tmp/_bike_test.png')
for name,rgb in DYES:
    paint(keys,uv,128,128,frame=rgb).save(RES+f'entity/bici_{name}.png')
o=[HEAD%('Modelo de la bicicleta','BikeModel','BikeEntity','bici','BikeModel','rueda_del','rueda_tras','rueda_del','rueda_tras')]
o.append('        root.addChild("bb_main", ModelPartBuilder.create()')
o.append('\n'.join('                '+cub(uv,k,d) for k,d in fixed)+',\n                ModelTransform.pivot(0F, 24F, 0F));')
for name,z in (('del',-12),('tras',12)):
    o.append(f'        ModelPartData rueda_{name} = root.addChild("rueda_{name}", ModelPartBuilder.create(), ModelTransform.pivot(0F, 18F, {f(z)}));')
    ring('tire','t_tire',keys['t_tire'][0],0,a16,f'rueda_{name}',uv,o)
    ring('spoke','t_spoke',keys['t_spoke'][0],0,[0,45,90,135],f'rueda_{name}',uv,o)
    o.append(f'        rueda_{name}.addChild("cubo", ModelPartBuilder.create(){cub(uv,"t_hub",keys["t_hub"][0])}, ModelTransform.NONE);')
ang=math.atan2(13,7)
for side,x in (('izq',-1.3),('der',1.3)):   # tirantes del sillin a la rueda trasera
    o.append(f'        root.addChild("tirante_{side}", ModelPartBuilder.create(){cub(uv,"stay",keys["stay"][0])}, ModelTransform.of({f(x)}, 11F, -1F, {f(ang)}, 0F, 0F));')
o.append(tail(128,128,'        wheelA.pitch = roll;\n        wheelB.pitch = roll;')%'BikeEntity')
s=open(JAVA+'BikeModel.java','w') if False else None
txt='\n'.join(o)
# BikeModel solo tiene 2 ruedas: se pasa el mismo hijo dos veces
txt=txt.replace('''        this.wheelC = root.getChild("rueda_del");
        this.wheelD = root.getChild("rueda_tras");
''','').replace('private final ModelPart wheelA, wheelB, wheelC, wheelD;','private final ModelPart wheelA, wheelB;')
open(JAVA+'BikeModel.java','w').write(txt)

# ============ ICONOS DE ITEM DE BICI (16x16) ============
for name,rgb in DYES:
    im=Image.new('RGBA',(16,16),(0,0,0,0)); d=ImageDraw.Draw(im)
    c=rgb+(255,); dk=(30,30,34,255); ch=(200,202,210,255)
    for cx in (3,12):
        d.ellipse([cx-3,8,cx+3,14],outline=dk); d.point((cx,11),fill=ch)
    d.line([(3,11),(7,6)],fill=c); d.line([(7,6),(12,11)],fill=c); d.line([(7,6),(10,5)],fill=c)
    d.line([(7,6),(7,11)],fill=c); d.line([(3,11),(7,11)],fill=c); d.line([(7,11),(12,11)],fill=c)
    d.line([(10,3),(13,3)],fill=dk); d.line([(11,3),(11,5)],fill=ch)    # manillar
    d.line([(5,4),(8,4)],fill=dk)                                       # sillin
    im.save(RES+f'item/bici_{name}.png')

# ============ AUTOBUS ============
YEL=(236,190,40); ROOF=(232,232,236); RED=(190,40,46); DRED=(140,28,34); SCREEN=(90,170,232)
keys={}; fixed=[]
def bx(k,x,y,z,w,h,d,c):
    if k in keys: assert keys[k][0][3:]==(w,h,d),k
    else: keys[k]=((0,0,0,w,h,d),c)
    fixed.append((k,(x,y,z,w,h,d)))
bx('piso',-20,-7,-36,40,2,72,DARK)
bx('pared',-20,-17,-36,1.5,10,72,YEL); bx('pared',18.5,-17,-36,1.5,10,72,YEL)
bx('frente',-18.5,-17,-36,37,10,1.5,YEL); bx('trasera',-18.5,-17,34.5,37,10,1.5,YEL)
bx('techo',-21,-44,-37,42,2,74,ROOF)
for x in (-20,18.5):
    for z in (-35.5,-24,-12,0,12,24,34.5): bx('pilar',x,-42,z,1.5,25,1.5,DARK)
bx('tablero',-18,-19,-34.5,36,4,5,DARK)
bx('pantalla',-6,-24,-33,12,6,1,SCREEN)
for i,(x,c) in enumerate(((-13,(230,60,60)),(-9.5,(60,200,90)),(6.5,(240,200,50)),(10,(70,120,230)))): bx('boton%d'%i,x,-21,-33,3,3,1,c)
for x in (-13,5):
    bx('asiento_cojin',x,-12,-26,8,2,7,RED); bx('asiento_base',x,-10,-26,8,3,7,DRED); bx('asiento_resp',x,-24,-21,8,12,2,RED)
bx('volante_a',5,-23,-28,8,1,1,BLACK); bx('volante_b',5,-16,-28,8,1,1,BLACK)
bx('volante_c',5,-23,-28,1,8,1,BLACK); bx('volante_d',12,-23,-28,1,8,1,BLACK); bx('columna',8.5,-19,-30,1,1,2.5,BLACK)
for z0 in (-14,-4,6,16,26):
    bx('sofa_cojin',-15,-12,z0,30,2,8,RED); bx('sofa_base',-15,-10,z0,30,3,8,DRED)
    bx('sofa_resp',-15,-24,z0+6,30,12,2,RED); bx('sofa_brazo',-1,-14,z0,2,4,8,DRED)
for cx in (-10,10):
    bx('ducto_x',cx-7,-48,-7,14,4,1.5,GRAY); bx('ducto_x',cx-7,-48,5.5,14,4,1.5,GRAY)
    bx('ducto_z',cx-7,-48,-5.5,1.5,4,11,GRAY); bx('ducto_z',cx+5.5,-48,-5.5,1.5,4,11,GRAY)
for x in (-12,12):
    bx('cohete',x-2.5,-22.5,37,5,5,10,WHITE); bx('tobera',x-1.5,-21.5,47,3,3,2.5,DARK)
    bx('aleta_v',x-0.4,-26.5,39,0.8,4,4,RED); bx('aleta_h',x-5,-20.4,39,10,0.8,4,RED); bx('soporte_cohete',x-1,-21.5,36,2,3,1,DARK)
keys['wheel']=((0,0,0,3,12,4.97),TIRE); keys['hub']=((0,0,0,3.6,4,4),HUB)
keys['blade_a']=((0,0,0,12,1,2),DARK); keys['blade_b']=((0,0,0,2,1,12),DARK); keys['fanhub']=((0,0,0,3,2,3),CHROME)
uv=pack(keys,256,300); paint(keys,uv,256,300).save(RES+'entity/autobus.png')
o=[HEAD%('Modelo del autobus volador','BusModel','BusEntity','autobus','BusModel','rueda_a','rueda_b','rueda_c','rueda_d')]
o.append('        root.addChild("bb_main", ModelPartBuilder.create()')
o.append('\n'.join('                '+cub(uv,k,d) for k,d in fixed)+',\n                ModelTransform.pivot(0F, 24F, 0F));')
for n,(px,pz) in zip('abcd',((-20.5,-24),(20.5,-24),(-20.5,22),(20.5,22))):
    o.append(f'        ModelPartData rueda_{n} = root.addChild("rueda_{n}", ModelPartBuilder.create(), ModelTransform.pivot({f(px)}, 18F, {f(pz)}));')
    ring('disc','wheel',keys['wheel'][0][3:],0,[0,45,90,135],f'rueda_{n}',uv,o)
    o.append(f'        rueda_{n}.addChild("cubo", ModelPartBuilder.create(){cub(uv,"hub",(-1.8,-2,-2,3.6,4,4))}, ModelTransform.NONE);')
for n,cx in (('izq',-10),('der',10)):
    o.append(f'        ModelPartData helice_{n} = root.addChild("helice_{n}", ModelPartBuilder.create(), ModelTransform.pivot({f(cx)}, -22F, 0F));')
    o.append(f'        helice_{n}.addChild("hojas", ModelPartBuilder.create(){cub(uv,"blade_a",(-6,-0.5,-1,12,1,2))}{cub(uv,"blade_b",(-1,-0.5,-6,2,1,12))}{cub(uv,"fanhub",(-1.5,-1,-1.5,3,2,3))}, ModelTransform.NONE);')
o.append(tail(256,300,'        wheelA.pitch = roll;\n        wheelB.pitch = roll;\n        wheelC.pitch = roll;\n        wheelD.pitch = roll;\n        root.getChild("helice_izq").yaw = spin;\n        root.getChild("helice_der").yaw = -spin;')%'BusEntity')
txt='\n'.join(o).replace('    public float roll;','    public float roll;\n    /** Giro de las helices del techo (radianes). */\n    public float spin;')
open(JAVA+'BusModel.java','w').write(txt)
# icono de item
im=Image.new('RGBA',(16,16),(0,0,0,0)); d=ImageDraw.Draw(im)
d.rectangle([1,5,14,11],fill=YEL+(255,)); d.rectangle([1,4,14,4],fill=ROOF+(255,))
for x in (2,5,8,11): d.rectangle([x,6,x+1,7],fill=(120,190,235,255))
d.rectangle([1,9,14,9],fill=(30,30,34,255)); d.ellipse([2,10,5,13],fill=(30,30,34,255)); d.ellipse([10,10,13,13],fill=(30,30,34,255))
d.rectangle([4,2,6,3],fill=(150,152,160,255)); d.rectangle([9,2,11,3],fill=(150,152,160,255)); d.point((14,8),fill=(230,60,60,255))
im.save(RES+'item/autobus.png')

print('ok')
