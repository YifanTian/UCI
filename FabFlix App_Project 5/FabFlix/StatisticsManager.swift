//
//  StatisticsManager.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

struct StatisticsManager
{
    // This class should fetch the appropriate statistical data from the database
    
    static func commitResult(correctCount: Int, incorrectCount: Int, averageTime: Int)
    {
        if let err = SD.executeChange(
            "INSERT INTO outcomes (correct_answers, incorrect_answers, average_time) VALUES (?, ?, ?)",
            withArgs: [correctCount, incorrectCount, averageTime])
        {
            // ERROR
        }
    }
    
    static func fetchStatistics() -> [String]
    {
        let (check, error) = SD.executeQuery("SELECT COUNT(*) as count FROM outcomes;")
        
        for row in check
        {
            let count: Int = (row["count"]?.asInt()!)!
            
            if count <= 0
            {
                return ["0", "0", "0", "0:00"]
            }
        }
        
        let (result, err) = SD.executeQuery(
            "SELECT COUNT(id) as count, SUM(correct_answers) as correct, " +
            "SUM(incorrect_answers) as incorrect, AVG(average_time) as time FROM outcomes;")
        
        var values: [String]? = ["0", "0", "0", "0:00"]
        
        for row in result
        {
            let count: Int = (row["count"]?.asInt()!)!
            let correct: Int = (row["correct"]?.asInt()!)!
            let incorrect: Int = (row["incorrect"]?.asInt()!)!
            let averageSeconds: Int = (row["time"]?.asInt()!)!
            
            var timeCopy = averageSeconds
            
            let minutes = timeCopy / 60
            timeCopy -= minutes * 60
            
            let seconds = timeCopy
            
            let minutesString = minutes > 9 ? String(minutes):"0" + String(minutes)
            let secondsString = seconds > 9 ? String(seconds):"0" + String(seconds)
            
            let averageTime: String = "\(minutesString):\(secondsString)"
            
            values = ["\(count)", "\(correct)", "\(incorrect)", averageTime]
        }
        
        return values!
    }
    
    static func resetStatistics()
    {
        let err = SD.executeChange("DELETE FROM outcomes")
    }
}