#############################
# Author: Joshua Cullings   #
#                           #
# Date Modified: 2/14/2019  #
#                           #
#############################

# taking the running average of images grayscaling and subtracting it from the current image

'''
App Instructions
----------------
ESC - close program
1   - toggle grayscale
2   - toggle absolute difference
3   - blur w/ average of 10x10
4   - accumulate weighted
5   - low threshold grayscale
6   - high threshold grayscale
7   - find contours
8   - bounding rectangles
'''

#importing the necessary packages
import numpy as np
import cv2 as cv
import time

#printing use instructions
print(__doc__)

#initializing variables
grayKey = 0
ad = 0
blur = 0
accWeight = 0
blurKey = np.ones((10,10),np.float32)/100
lowThresh = 0
highThresh = 0
findC = 0
store = 0
rect = 0

#src = cv.VideoCapture(0)
#previous = src.read()

frame = cv.imread("candy.jpg", cv.IMREAD_COLOR)
while(True):
    #tf, frame = src.read()
    image1 = frame
    acc = np.float32(image1)
    gray = cv.cvtColor(frame, cv.COLOR_BGR2GRAY)

    if ad == 1 or lowThresh == 1 or highThresh == 1:
        grayKey = 1

    #grayscale functionality
    if grayKey == 1:
        image1 = cv.cvtColor(image1, cv.COLOR_BGR2HSV)       

    #absdiff functionality
    if ad == 1:
        previous = cv.cvtColor(previous, cv.COLOR_BGR2GRAY)
        cv.absdiff(previous, image1, image1)

    #blur functionality
    if blur == 1:
        image1 = cv.filter2D(image1, -1, blurKey)

    #accumulate weighted functionality
    if accWeight == 1:
        cv.accumulateWeighted(frame, acc, 0.32)
        accWeightHolder = cv.convertScaleAbs(acc)
        print(accWeightHolder)

    #low threshold functionality
    if lowThresh == 1:
        holder, image1 = cv.threshold(image1, 20, 255, cv.THRESH_BINARY)

    #high threshold functionality
    if highThresh == 1:
        holder, image1 = cv.threshold(image1, 200, 255, cv.THRESH_BINARY)

    #find contours functionality
    if findC == 1:
        #gray = cv.bitwise_not(gray)
        gray = cv.filter2D(gray, -1, blurKey)
        holder, gray = cv.threshold(gray, 127, 255, cv.THRESH_BINARY)
        image1 = gray
        contours, _ = cv.findContours(gray, cv.RETR_TREE, cv.CHAIN_APPROX_SIMPLE)
        for contour in contours:
            area = cv.contourArea(contour)
            if (area>1000):
                cv.drawContours(frame, contour, -1, (0, 0, 255), 2)

    #Draws bounding boxes centered on the significant blobs
    if rect == 1:
        #gray = cv.bitwise_not(gray)
        gray = cv.filter2D(gray, -1, blurKey)
        holder, gray = cv.threshold(gray, 100, 255, cv.THRESH_BINARY)
        contours, _ = cv.findContours(gray, cv.RETR_TREE, cv.CHAIN_APPROX_SIMPLE)
        for contour in contours:
            area = cv.contourArea(contour)
            if (area>2000 and area<50000):
                x, y, z, h = cv.boundingRect(contour)
                cv.rectangle(frame, (x, y), (x+z, y+h), (0, 0, 255), 2)

    cv.imshow('image1', image1)
    
    #print(frame)   
    cv.imshow('Live Feed', frame)
    
    keyboard = cv.waitKey(100)
    if keyboard == 27:
        break

    #setting grayscale to 1
    elif keyboard == 49:
        grayKey = grayKey+1
        grayKey = grayKey%2

    #setting absolute difference to 2
    elif keyboard == 50:
        ad = ad+1
        ad = ad%2

    #setting blur to 3
    elif keyboard == 51:
        blur = blur+1
        blur = blur%2

    #setting accumulate weighted to 4
    elif keyboard == 52:
        accWeight = accWeight+1
        accWeight = accWeight%2

    #setting low threshold grayscale to 5
    elif keyboard == 53:
        lowThresh = lowThresh+1
        lowThresh = lowThresh%2

    #setting high threshold grayscale to 6
    elif keyboard == 54:
        highThresh = highThresh+1
        highThresh = highThresh%2

    #setting find contours to 7
    elif keyboard == 55:
        findC = findC+1
        findC = findC%2

    #setting bounding boxes to 8
    elif keyboard == 56:
        rect = rect+1
        rect = rect%2

    #sets the previous frame for future use
    previous = frame

#closes windows and stops using webcam
src.release()
cv.destroyAllWindows()

"""
    flipKey = 0
    
    #flip functionality
    if flipKey == 1:
        image1 = cv.flip(image1, 0)

    elif keyboard == 52:
        flipKey = flipKey+1
        flipKey = flipKey%2
"""

#####################################
# RESOURCES USED:                   #
# - opencv/sources/samples/python   #
# - https://stackoverflow.com/      #
#   questions/13538748/crop-black-  #
#   edges-with-opencv               #
#####################################

