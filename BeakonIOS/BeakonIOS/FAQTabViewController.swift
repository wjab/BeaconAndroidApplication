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
        let defaults = NSUserDefaults.standardUserDefaults()
        let points = defaults.objectForKey("points") as! Int
        //Button abre  menu
        var open = UIButton()
        let image = defaults.objectForKey("image")as! String
        let typeUser = defaults.objectForKey("socialNetworkType")as! String
        open = Utils.loadMenuButton(open, image: image, typeUser: typeUser)
        open.frame = CGRectMake(0, 0, 40, 35)
        open.layer.masksToBounds = false
        open.layer.cornerRadius = open.frame.height/2
        open.clipsToBounds = true
        open.addTarget(self, action: #selector(FAQTabViewController.openMenu), forControlEvents: .TouchUpInside)
        self.navigationItem.setLeftBarButtonItem(UIBarButtonItem(customView: open), animated: true)
        //Set verde en el tab seleccionado
        let numberOfItems = CGFloat(tabBar.items!.count)
        let tabBarItemSize = CGSize(width: tabBar.frame.width / numberOfItems, height: tabBar.frame.height)
        tabBar.selectionIndicatorImage = UIImage.imageWithColor(Constants.colors.getDarkGreen(), size: tabBarItemSize).resizableImageWithCapInsets(UIEdgeInsetsZero)
        //Cambia el tamaño de los tabs
      //  let yStatusBar = UIApplication.sharedApplication().statusBarFrame.size.height
       // tabBar.frame = CGRectMake(0, 0 + yStatusBar + tabBarFaq.frame.size.height-15, tabBarFaq.frame.size.width, tabBarFaq.frame.size.height-15)
        
        //Cambia el color de los tabs
        UITabBarItem.appearance().setTitleTextAttributes([NSForegroundColorAttributeName: Constants.colors.getBlack()], forState: UIControlState.Normal)
        UITabBarItem.appearance().setTitleTextAttributes([NSForegroundColorAttributeName: Constants.colors.getWhite()], forState: UIControlState.Selected)
        UITabBarItem.appearance().titlePositionAdjustment = UIOffset(horizontal: 0, vertical: -13)
        //Genera el boton de la derecha que contiene el corazon que abre la lista de deseos
        let btn1 = UIButton()
        btn1.setImage(UIImage(named: "icon_added"), forState: .Normal)
        btn1.frame = CGRectMake(0, 0, 30, 25)
        btn1.addTarget(self, action: #selector(FAQTabViewController.openWishList), forControlEvents: .TouchUpInside)
        self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);
        //Genera el boton del centro que contiene los puntos del usuario
        let button =  UIButton(type: .Custom)
        button.frame = CGRectMake(0, 0, 100, 40) as CGRect
        button.setTitle(String(points), forState: UIControlState.Normal)
        button.addTarget(self, action: #selector(FAQTabViewController.clickOnButton(_:)), forControlEvents: UIControlEvents.TouchUpInside)
        self.navigationItem.titleView = button
    }
    //Abre el historial de puntos
    func clickOnButton(button: UIButton) {
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("HistoryPointsViewController") as! HistoryPointsViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    //Abre el menu
    func openMenu(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("MenuContainerViewController") as! MenuContainerViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    //Abre la lista de deseos
    func openWishList(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("WishViewController") as! WishViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
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
