//
//  StatisticsViewController.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import UIKit

class StatisticsViewController: UITableViewController, UITableViewDelegate, UITableViewDataSource {

    @IBOutlet weak var label: UILabel?
    
    var titles:[String] = ["Number of Quizzes taken", "Total Correct / Total Score", "Total Incorrect", "Avg Time per Question"]
    
    var statistics: [String]?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        refreshStatistics()
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        
        refreshStatistics()
    }
    
    // MARK: UITableViewDataSource
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return titles.count
    }
   
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("StatisticsCell") as UITableViewCell
        
        cell.textLabel?.text = titles[indexPath.row] + ": "
        cell.detailTextLabel?.text = statistics?[indexPath.row]
        
        return cell
    }
    
    func refreshStatistics()
    {
        self.statistics = StatisticsManager.fetchStatistics()
        self.tableView.reloadData()
    }
    
    @IBAction func resetStatistics(sender: AnyObject)
    {
        StatisticsManager.resetStatistics()
        refreshStatistics()
    }
}

