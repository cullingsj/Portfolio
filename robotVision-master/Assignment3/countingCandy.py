#############################
# Author: Joshua Cullings   #
# Date: 2/24/2019           #
#                           #
#############################

# taking the running average of images grayscaling and subtracting it from the current image
'''
App Instructions
----------------------------------------------
----------------------------------------------
ESC - close program
1   - show edges
'''

#importing the necessary packages
import numpy as np
import cv2 as cv
import time

print(__doc__)

counter = 0

frame = cv.imread('candy.jpg')

def countingCandy():
    blur = cv.medianBlur(frame, 5)
    edges = cv.Canny(blur, 400, 100)
    cv.imshow("Edges", edges)
    circles = cv.HoughCircles(edges, cv.HOUGH_GRADIENT, dp = 2,
                              minDist = 50, param1 = 300,
                              param2 = 35, minRadius = 50,
                              maxRadius = 65)
    if circles is not None:
        circles = np.round(circles[0,:]).astype("int")
        for i in circles:
            
            cv.circle(frame, (i[0], i[1]), i[2], (0, 0, 255), 3)
    else:
        print("Failed to find candy")
    
while(True):
    b, g, r = cv.split(frame)
    cv.imshow("Live Feed", frame)
    cv.imshow("Blue", b)
    cv.imshow("Green", g)
    cv.imshow("Red", r)
    #if counter == 1:
        
    keyboard = cv.waitKey(33)
    if keyboard == 27:
        break

    #setting hsv conversion to 1
    elif keyboard == 49:
        counter = counter+1
        counter = counter%2
        countingCandy()
        
#src.release()
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
