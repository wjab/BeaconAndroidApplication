//
//  FAQTabViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/26/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class FAQTabViewController: UITabBarController {

    @IBOutlet weak var tabBarFaq: UITabBar!
    override func viewDidLoad() {
        super.viewDidLoad()
        let yStatusBar = UIApplication.sharedApplication().statusBarFrame.size.height
        tabBar.frame = CGRectMake(0, 0 + yStatusBar + tabBarFaq.frame.size.height-30, tabBarFaq.frame.size.width, tabBarFaq.frame.size.height-30)
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
