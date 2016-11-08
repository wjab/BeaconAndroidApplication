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
    var btn1 = UIButton()
    let defaults = NSUserDefaults.standardUserDefaults()
    var wishCount = 1
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        self.navigationItem.title = ""
        
        self.wishCount = defaults.objectForKey("wishCount")as!Int
        let points = defaults.objectForKey("points") as! Int
        
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
        btn1 = Utils.loadWishListButton(btn1, wishCount: wishCount)
        btn1.addTarget(self, action: #selector(FAQTabViewController.openWishList), forControlEvents: .TouchUpInside)
        self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);
        
        // Crea el view con el label de puntos y el arrow de imagen
        let myView = Utils.createPointsView(points, activateEvents: true)
        let gesture = UITapGestureRecognizer(target : self, action: #selector(FAQTabViewController.clickOnButton))
        myView.addGestureRecognizer(gesture)
        self.navigationItem.titleView = myView
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(FAQTabViewController.refreshWishCount),name:"refreshWishCountFaq", object: nil)
    }
    
    //Refresca el contador
    func refreshWishCount(){
       self.wishCount = defaults.objectForKey("wishCount")as!Int
        btn1.setTitle(String(self.wishCount), forState: .Normal)
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
}
