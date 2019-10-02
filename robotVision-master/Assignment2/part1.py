#############################
# Author: Joshua Cullings   #
# Date: 2/10/2019           #
#                           #
#############################

# taking the running average of images grayscaling and subtracting it from the current image
'''
App Instructions
----------------------------------------------
Click in HSV window to select a color to track
----------------------------------------------
ESC - close program
1   - toggle HSV
2   - track white object
3   - dilate
4   - erode
'''

#importing the necessary packages
import numpy as np
import cv2 as cv
import time

print(__doc__)

hsvKey = 0
trackImg = 0
dil = 0
erode = 0
blurKey = np.ones((10,10),np.float32)/100
x = 0
y = 0
sliderSize = np.zeros((100, 500), np.uint8)
lower = np.array([0,0,0], np.uint8)
upper = np.array([0,0,255], np.uint8)
src = cv.VideoCapture(0)
previous, frame = src.read()
hsv = cv.cvtColor(frame, cv.COLOR_BGR2HSV)
dilateNerode = np.ones((5,5), np.uint8)

def setLU(event, x, y, flags, params):
    global newU
    if event == cv.EVENT_LBUTTONDOWN:
        newU = np.array(hsv[x,y])
        print('x is '+str(x)+'\ny is '+str(y)+'\nThe value is '+str(newU))
        
def setHueUpper(h):
    pass

def setHueLower(h):
    pass

def setSatUpper(s):
    pass

def setSatLower(s):
    pass

def setValueUpper(v):
    pass

def setValueLower(v):
    pass

cv.namedWindow("Live Feed")
cv.namedWindow("HSV")
cv.namedWindow("Track Image")
cv.namedWindow("Sliders")

cv.createTrackbar("Hue Upper", "Sliders", 0, 255, setHueUpper)
cv.createTrackbar("Sat Upper", "Sliders", 0, 255, setSatUpper)
cv.createTrackbar("Val Upper", "Sliders", 0, 255, setValueUpper)
cv.createTrackbar("Hue Lower", "Sliders", 0, 255, setHueLower)
cv.createTrackbar("Sat Lower", "Sliders", 0, 255, setSatLower)
cv.createTrackbar("Val Lower", "Sliders", 0, 255, setValueLower)

cv.imshow("Sliders", sliderSize)
frame = cv.imread("candy.jpg")
while(True):
    #tf, frame = src.read()
    hsv = cv.cvtColor(frame, cv.COLOR_BGR2HSV)
    cv.setMouseCallback("Live Feed", setLU)
    cv.imshow("Live Feed", frame)
    
    #hsv functionality
    if hsvKey == 1:
        cv.imshow("HSV", hsv)
    else:
        cv.destroyWindow("HSV")

    if trackImg == 1:
        hueU = cv.getTrackbarPos("Hue Upper", "Sliders")
        saturationU = cv.getTrackbarPos("Sat Upper", "Sliders")
        valueU = cv.getTrackbarPos("Val Upper", "Sliders")
        hueL = cv.getTrackbarPos("Hue Lower", "Sliders")
        saturationL = cv.getTrackbarPos("Sat Lower", "Sliders")
        valueL = cv.getTrackbarPos("Val Lower", "Sliders")
        upper = np.array([hueU, saturationU, valueU], np.uint8)
        lower = np.array([hueL, saturationL, valueL], np.uint8)
        track = cv.filter2D(hsv, -1, blurKey)
        tracker = cv.inRange(track, lower, upper)
        if dil == 1:
            tracker = cv.dilate(tracker, dilateNerode, 1)

        if erode ==1:
            tracker = cv.erode(tracker, dilateNerode, 1)
            
        cv.imshow("Track Image",tracker)
    else:
        cv.destroyWindow("Track Image")

    keyboard = cv.waitKey(33)
    if keyboard == 27:
        break

    #setting hsv conversion to 1
    elif keyboard == 49:
        hsvKey = hsvKey+1
        hsvKey = hsvKey%2

    #setting track image to 2
    elif keyboard == 50:
        trackImg = trackImg+1
        trackImg = trackImg%2

    elif keyboard == 51:
        dil = dil+1
        dil = dil%2

    elif keyboard == 52:
        erode = erode+1
        erode = erode%2
        
    previous = frame
        
src.release()
cv.destroyAllWindows()

#Extra code for flip functionality
"""        if flipKey == 1:
            image1 = cv.flip(image1, 0)
            cv.imshow('image1', image1)
            
    elif flipKey == 1:
        image1 = cv.flip(image1, 0)
        cv.imshow('image1', image1)
"""
#####################################
# RESOURCES USED:                   #
# - opencv/sources/samples/python   #
# - https://stackoverflow.com/      #
#   questions/13538748/crop-black-  #
#   edges-with-opencv               #
# - https://www.opencv-srf.com/2011 #
#   /11/mouse-events.html           #
#                                   #
#####################################
