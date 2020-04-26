# 594FinalProject
by Lu Liu and Kai Kleinbard
4/25/2020
UPENN MCIT

Dear TA:

```diff
- please note that our program on initialization, takes about 2 - 3 minutes to load. 
- Then it works fast. During that time, it is reading the files, including the large properties csv file.
```


## The additional feature:
We are finding the zipcode with the highest total parking fines (once we've added all the fines within the neighborhood). Then we use this data to identify what that same zipcodes total livable area per capita. We are curious how parking fines might relate to total  livable area. We know this is correct, because our code increments each fine with all the other fines in that same zipcode. Then we find the zipcode with the highest total. We know that our livable area per capita is correct when we take a general average of most livable areas we get a number close to the one we have. We also tested this out on smaller data and also our code generates the same number for the json and csv files. In addition, we're taking the numbers we've already identified, adding all the livable areas of each space and then dividing that by the total population of that zipcode. 

## Three Data Structures:
HashMap: We used a hashmap in multiple places within the reader classes. We used a map, because we wanted to have a key, which was the zipcode, which represented a value. For example, for the property reader, we stored the zipcode as key, and the PropertyValue object. We considered a number of other structures, such as an ArrayList and HashSet. The ArrayList we thought might be great if we created a more robust PropertyValue object that held zipcodes. We also considered a HashSet, because it only stores one of each item -- since all zipcodes are unique this seemed like a viable option. In the end, we chose HashMap since, like a HashSet, has only unique values, but we decided to keep zipcodes separate from the PropertyValue, object, as it also allowed us to use HashMap for ParkingViolations and Population, using the same storage logic, with zipcode as key, and corresponding data as value. One disadvantage of the HashMap is it takes more overhead to store, however, this was okay with us in this case, as we were not concerned with memory.

TreeMap:  We used a treemap to store the parking violations per capita data. Since we were asked to store these based on ascending numerical order of zipcodes. Since a TreeMap automatically sorts data, it was the best data structure for this case. We recognized that TreeMap has limitations and is slower for operations like adding, removing and contains.  Another structure we considered here, HashMap, can do the same operations in O(1) (while TreeMap is O(log(N))]. However, we appreciated TreeMap's ability to sort items as they’re entered. We considered a HashSet here, since all the zipcodes are unique, however, it did not have the ability to sort automatically as a TreeMap does.

ArrayList: We used an ArrayList to store all the PropertyValues within a map entry. One zipcode generally has many thousands of entries and we wanted a quick way to index and get property values if necessary. Since ArrayLists, unlike arrays, automatically resize, this was a good option as we did not know how many values were in each zipcode. In addition, we wanted our program to be expandable in case we ever used a different city or file, etc. ArrayLists give us a quick way to index and get values. We considered an array here, but we would have to know the full size. We also considered a HashSet, but we realize that many properties have the same livable and market value, and HashSets don't allow duplicate values. Thus ArrayList was the best option for us. 

Array: We used arrays in multiple areas, especially in readers to quickly split files by commas, spaces and quotations. Arrays offered us a quick and relatively way to index and store the cells of our CSV. Direct indexing takes O(1). On the other hand searching is rather slow, at O(N), but since we were splitting each line as we were reading it, our ability to get the relevant data by index was key here (we did not have to sort or search for anything, we just had to get to a certain index). We considered an ArrayList here, which would have been sufficient, but the convenient String.split() method in Java makes Arrays relatively useful in this case. We also considered a StringBuilder, where we search character by character, adding into our StringBuilder, however, this proved cumbersome and required more code for the StringBuilder to constantly be turned into a String and then refreshed/cleared.


