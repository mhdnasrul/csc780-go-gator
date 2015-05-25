# **Recommended reading for flow of Code** #


## **screen.main** ##

  * [GoGatorActivity.java](http://code.google.com/p/csc780-go-gator/source/browse/trunk/goGator/src/screen/main/GoGatorActivity.java)

(Main entry point for Application - Tab Activity to load level1 Activities)



## **screen.level1** ##

  * [HomeActivity.java](http://code.google.com/p/csc780-go-gator/source/browse/trunk/goGator/src/screen/level1/HomeActivity.java)

(Entry point for any type of destination Navigation - Activity to load level2 Activities)

  * [MapsActivity.java](http://code.google.com/p/csc780-go-gator/source/browse/trunk/goGator/src/screen/level1/MapsActivity.java)

(Very Important part of Navigation, it loads map and all the correct path to direct user here.)

<<If only willing to read one class, I would recommend to go through code of this class. >>

  * [CameraActivity.java](http://code.google.com/p/csc780-go-gator/source/browse/trunk/goGator/src/screen/level1/CameraActivity.java)

(Point It functionality is implemented here.)

  * [MoreActivity.java](http://code.google.com/p/csc780-go-gator/source/browse/trunk/goGator/src/screen/level1/MoreActivity.java)

(Small Description of About Us)



## **screen.level2 & screen.level3** ##

> (All the List Adapters are loaded here for Navigation & their description)



## **main.routing.algo** ##

> (Dijkstra Algorithm, Routing Dictionary to Generate Campus Graph and other related classes are in this package)



## **gatorDB** ##

  * [DataBaseHelper.java](http://code.google.com/p/csc780-go-gator/source/browse/trunk/goGator/src/gatorDB/DataBaseHelper.java)

> (To pull out values from DB)


## **Other packages e.g. Utilities, Overlays** ##

(To pull in common functionalities like alert, toast, distance Calculation )

(Overlays to provide styling over existing default properties. )


## **Dependent App Used - ZXing** ##
(For QR COde scanning a separate App called ZXing is used - Code is set that when GoGator App is downloaded this app will also be automatically downloaded. User will not have to do anything.)






---


---


---


# **Week8 (11/17 - 12/1)** #

## **Completed:** ##

  * Campus Navigation and Building Navigation data placed and accessed through Database.

  * QRScan button placed on MapsActivity and current location.QRScan code and Building remap code is added to give a nice complete feeling to the App's Navigation. Also MyVisit Items List is switched back to normal.

  * PopUp Balloon is added for markers on the map. Default Text is set for Balloon Overlay.Replaced all building makers to reset Navigation.

  * Code CleanUp is done for MapsActivity. Application Flow for MapsActivity is streamlined. MapsActivity flow is completely connected.

  * For PointIt functionality, data required like GPS coordinates and bearing is fetched.

## **In progress:** ##

  * As the whole map rotates, the arrow always points to North. Like this the user knows if he is heading east or southwest or the like.

  * Implementation of MyRide

  * For PointIt,  A part of coding is left to actually pull up right building name using the fetched data i.e. GPS and Bearing is left.


---

# **Week7 (11/10 - 11/17)** #

## **Completed:** ##

  * The whole map rotates as the user rotates the phone. The user can orient the phone to a particular direction and obtain a mapview in that orientation

  * Improved performance to calculate and load path used in Campus Navigation by a big factor.

  * Created a SQLite database named gatorDB for storing geopoints, name and description.

  * ListView for MyCafe is displayed through a handler of the database.

## **In progress:** ##

  * As the whole map rotates, the arrow always points to North. Like this the user knows if he is heading east or southwest or the like.

  * Implementation of MyRide

  * Using database handlers for campus and building activities.

  * Showing popup on the markers which leads to MapIt button.



---

# **Week5&6 (10/27 - 11/10)** #

## **Completed:** ##

  * Added Map data for the whole campus in the routedictionary for Dijkstra's algorithm

  * The current location arrow rotates with the magnetometer sensor. Testing was done on the device.

  * Building Navigation and QRScan are working. Needs performance improvements. The QR codes contain the steps of navigating inside building.
The screenshot for building navigation is below.

![http://csc780-go-gator.googlecode.com/svn/img/BuildingNavigation.png](http://csc780-go-gator.googlecode.com/svn/img/BuildingNavigation.png)

  * Changed buttons to image buttons with appropriate images.

  * Prepared slides for class presentation.

## **In progress:** ##

  * Performance improvement for QRScan

  * Rotate map along with the arrow.

  * Create a database for geopoints and description

  * Showing popup on the markers which leads to MapIt button.



---

# **Week4 (10/20 - 10/27)** #

## **Completed:** ##

  * Instead of Google API call to find shortest path, Dijkstra is implemented to find shortest path in the campus.
The path is drawn using Dijkstra's algorithm instead of Google kml. The screen shot is shown below.

http://csc780-go-gator.googlecode.com/svn/img/dijkstras.JPG

  * We can now fetch current Location from WiFi and 3G when GPS is not available.

  * Fetched GeoPoints for the buildings and paths on the whole campus.

  * Installed goGator on a device (borrowed from Prof. Puder) and tested the working of navigation.
The screen shot is attached below.

http://csc780-go-gator.googlecode.com/svn/img/photoDevice.JPG

## **In progress:** ##

  * Rotate map with Magnetometer sensor. This makes it simpler for the user to judge left/right turns.

  * Showing popup on the markers which leads to MapIt button.



---

# **Week3 (10/13 - 10/20)** #

## **Completed:** ##

  * Instead of a dot showing current geolocation, the current location of the user is represented by an arrow now. The arrow has the capability of calculating the angle and pointing to any geolocation given as its input. The default geolocation is set to the center of the SFSU Campus. Also, upon rigorous testing we found minor degree of error in the directions for geolocations that are very far apart . The minor error is due to the planar projection of geopoints. Since our scope of App is within the campus it gives considerably well accuracy. There are formulas that exist to get the perfect accuracy but it compromises with performance and so I reduced it to simple formula and higher accuracy.

Screen shots for the above functionality is shown below

http://csc780-go-gator.googlecode.com/svn/img/MapTab.JPG

  * The user can choose the destination from the list view and Map it. When the MapIt button is clicked, the path from the current location to the destination is highlighted. The pointer directs the user to the destination through the intermediate geopoints. We tested several cases and verified the obtained directions.

Screen shots for directions to Administration Building are shown below

http://csc780-go-gator.googlecode.com/svn/img/MapIt%20-%201.JPG

http://csc780-go-gator.googlecode.com/svn/img/MapIt%20-%202.JPG

http://csc780-go-gator.googlecode.com/svn/img/MapIt%20-%203.JPG


  * The datastructure to hold manually recorded 'path' & 'building' geopoints of the campus is ready. Also, Dijkstra's Algorithm is implemented to calculate the shortest distance between the geopoints using the newly implemented Data Structure. Overall plan is to shift from Google URI calling to manually calculate the path. Rough testing for this code is done to see it is compiled and working. Will develop unit test case to see the code is working perfectly with set of geopoints and then integrate it in place of google URI Call.

  * We are using Google code's Issues feature to maintain a log of issues and enhancements. It also lets us set milestones for each functionality. We regularly test each others implementations to maintain good code quality.

## **In progress:** ##

  * Integration of Dijkstra's algorithm in place of Google's URI calls.

  * Developing unit test cases for this functionality and adding more intermediate geopoints.

  * Rotate map with Magnetometer sensor. This makes it simpler for the user to judge left/right turns.

  * Initiate Building Navigation functionality.

  * Showing popup on the markers which leads to MapIt button.





---


# **Week2 (10/6 - 10/13)** #

## **Completed:** ##

  * Added markers to map for all the buildings on campus
> > Markers are added to the map according to the latitude and longitude points.

  * Added View connectors between screen.level2 and screen.level3
> > The item that was clicked on in the list view of screen.level2 is passed as an intent value to the view screen screen.level3. The clicked item is showed in the MyCampusDetail screen.

  * Added GPS Current location for MyRide
> > The current location can be obtained on the map by providing geo coordinates through DDMS or telnet using google API.

  * Added walking distance between 2 geo locations, where first is Current geo location
> > The route between any two locations can be obtained by the MapActivity by setting the destination location through DDMS or telnet.

## **In progress:** ##

  * GPS and Navigation for building coordinates
> > Improvement of the route calculation and geo coordinate specification. Creating our own routing table and direction specification instead of using google API.

  * Refining map overlay for displaying markers corresponding to the list view item.



---


# **Week1 (9/29 - 10/6)** #

## **Completed:** ##

  * Created the home screen and tab bar

  * Created list views for MyCampus, MyCafe, MyVisit, MyRide

  * Displayed Map and Camera on clicking the respective buttons in the tab bar.

  * Displayed Map when MapIt button is clicked in the list-item description screen and MyRide screen

  * Displayed description in the About us screen

## **In progress:** ##

  * GPS and Navigation

  * Data population and Icon graphics

## **References:** ##

  * xmlvm.org/tutorial

  * developer.android.com

  * stackoverflow.com

