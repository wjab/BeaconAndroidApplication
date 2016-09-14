//
//  PointsTabViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/26/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class PointsTabViewController: UITabBarController {
    @IBOutlet weak var tabBarPoints: UITabBar!
    override func viewDidLoad() {
        super.viewDidLoad()
        let yStatusBar = UIApplication.sharedApplication().statusBarFrame.size.height
        tabBar.frame = CGRectMake(0, 0 + yStatusBar + tabBarPoints.frame.size.height-30, tabBarPoints.frame.size.width, tabBarPoints.frame.size.height-30)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
