//
//  ResultsViewController.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import UIKit

class ResultsViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    @IBOutlet weak var doneButton: UIButton!
    
    var titles: [String] = ["Total Correct", "Total Incorrect", "Avg Time per Question"]
    var values: [String]?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        var blueColor: UIColor = UIColor(red: 13/255, green: 122/255, blue: 254/255, alpha: 1)
        
        self.doneButton.layer.cornerRadius = 5
        self.doneButton.clipsToBounds = true
        self.doneButton.layer.borderWidth = 1
        
        self.doneButton.layer.borderColor = blueColor.CGColor
        self.doneButton.backgroundColor = UIColor.whiteColor()
        
        self.doneButton.titleLabel?.textColor = blueColor
    }
    
    @IBAction func endResultsView(sender: AnyObject)
    {
        self.presentingViewController?.presentingViewController?.dismissViewControllerAnimated(true, completion: nil)
    }
    
    // MARK: UITableViewDataSource
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return titles.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("ResultCell") as UITableViewCell
        
        cell.textLabel?.text = titles[indexPath.row] + ": "
        cell.detailTextLabel?.text = values?[indexPath.row]
        
        return cell
    }
    
    // MARK: UINavigationBarDelegate
    
    func positionForBar(bar: UIBarPositioning) -> UIBarPosition {
        return UIBarPosition.TopAttached
    }
}
