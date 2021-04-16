/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Comparator;

// TODO: Added comparator class to sort in the descending order
/**
 *
 * @author Dharshana
 */
public class NewsEntitySorter implements Comparator<NewsEntitySorted> {
    
    public int compare(NewsEntitySorted news1, NewsEntitySorted news2){
        return news2.getDate().compareTo(news1.getDate());
    }
}
