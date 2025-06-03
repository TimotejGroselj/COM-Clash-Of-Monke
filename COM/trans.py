import numpy as np
import matplotlib.image as im
import os

dirs = set(["animations", "pictures", "layout_pictures"])
#direktori za obdelat
for dir in dirs:
    files = os.listdir(dir)
    for file in files:
        img = im.imread(dir+"\\"+file)
        h, w, c = img.shape 
        #če ima slika transparentnost vse pixle, ki niso dovolj "močni" naredimo nevidne
        #ostale pa nastavimo da so polno transparentni
        if c == 4:
            for i in range(h):
                for j in range(w):
                    if img[i][j][3] <=0.3:
                        img[i][j][3] = 0
                    else:
                        img[i][j][3] = 1
        im.imsave(dir+"\\"+file ,img)
