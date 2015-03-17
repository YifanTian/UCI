//
//  DatabaseManager.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

import SwiftCSV

class DatabaseManager
{
    class func initialiseDatabase()
    {
        SD.executeMultipleChanges([
            "CREATE TABLE IF NOT EXISTS movies (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "title TEXT NOT NULL, " +
                "year INTEGER NOT NULL, " +
                "director TEXT NOT NULL " +
            ");",
            "CREATE TABLE IF NOT EXISTS stars (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "first_name TEXT NOT NULL, " +
                "last_name TEXT NOT NULL " +
            ");",
            "CREATE TABLE IF NOT EXISTS stars_in_movies (" +
                "star_id INTEGER NOT NULL, " +
                "movie_id INTEGER NOT NULL, " +
                "FOREIGN KEY (star_id) REFERENCES stars(id), " +
                "FOREIGN KEY (movie_id) REFERENCES movies(id) " +
            ");",
            "CREATE TABLE IF NOT EXISTS outcomes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "correct_answers INTEGER NOT NULL, " +
                "incorrect_answers INTEGER NOT NULL, " +
                "average_time INTEGER NOT NULL" +
            ");"])
    }
    
    class func populateDatabase()
    {
        parseMoviesFile(NSBundle.mainBundle().URLForResource("movies", withExtension: "csv")!)
        parseStarsFile(NSBundle.mainBundle().URLForResource("stars", withExtension: "csv")!)
        parseStarsInMoviesFile(NSBundle.mainBundle().URLForResource("stars_in_movies", withExtension: "csv")!)
    }
    
    class func parseMoviesFile(fileURL: NSURL)
    {
        var error: NSErrorPointer = nil
        
        if let csv = CSV(contentsOfURL: fileURL, error: error)?
        {
            if csv.rows.count > 0
            {
                SD.executeChange("DELETE FROM movies;")
            }
            
            for row in csv.rows
            {
                let id: String = row["id"]!
                let title: String = row["title"]!
                let year: String = row["year"]!
                let director: String = row["director"]!
                
                SD.executeQuery("INSERT INTO movies (id, title, year, director) VALUES (?, ?, ?, ?)", withArgs: [id, title, year, director])
            }
        }
    }
    
    class func parseStarsFile(fileURL: NSURL)
    {
        var error: NSErrorPointer = nil
        
        if let csv = CSV(contentsOfURL: fileURL, error: error)
        {
            if csv.rows.count > 0
            {
                SD.executeChange("DELETE FROM stars;")
            }
            
            for row: Dictionary in csv.rows
            {
                let id: String = row["id"]!
                let firstName: String = row["first_name"]!
                let lastName: String = row["last_name"]!
                
                SD.executeQuery("INSERT INTO stars (id, first_name, last_name) VALUES (?, ?, ?)", withArgs: [id, firstName, lastName])
            }
        }
    }
    
    class func parseStarsInMoviesFile(fileURL: NSURL)
    {
        var error: NSErrorPointer = nil
        
        if let csv = CSV(contentsOfURL: fileURL, error: error)
        {
            if csv.rows.count > 0
            {
                SD.executeChange("DELETE FROM stars_in_movies;")
            }
            
            for row: Dictionary in csv.rows
            {
                let starId: String = row["star_id"]!
                let movieId: String = row["movie_id"]!
                
                SD.executeQuery("INSERT INTO stars_in_movies (star_id, movie_id) VALUES (?, ?)", withArgs: [starId, movieId])
            }
        }
    }
}