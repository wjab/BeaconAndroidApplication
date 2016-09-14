//
//  MenuViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/25/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Haneke
protocol SlideMenuDelegate {
    func slideMenuItemSelectedAtIndex(index : Int32)
}

class MenuViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {

    @IBOutlet var tblMenuOptions : UITableView!
    @IBOutlet var btnCloseMenuOverlay : UIButton!
    var arrayMenuOptions = [Dictionary<String,String>]()
    var btnMenu: UIButton!
    var delegate : SlideMenuDelegate?
    var positionLogOut = 0
    @IBOutlet weak var pointsLabel: UILabel!
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var imageProfile: UIImageView!
    override func viewDidLoad() {
        super.viewDidLoad()
        tblMenuOptions.tableFooterView = UIView()
        let defaults = NSUserDefaults.standardUserDefaults()
        let username = defaults.objectForKey("username") as? String
        let image = defaults.objectForKey("image") as? String
        let points = defaults.objectForKey("points") as! Int
        let url = NSURL(string: image!)!
        imageProfile?.hnk_setImageFromURL(url, format: Format<UIImage>(name: "original"))
        //imageByRoundingCornersOfImage(image)
        
        usernameLabel?.text = username
        pointsLabel?.text = String(points)
        // Do any additional setup after loading the view.
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        updateArrayMenuOptions()
    }
    
    
    func updateArrayMenuOptions(){
        arrayMenuOptions.append(["title":"Inicio", "icon":"icon_home"])
        arrayMenuOptions.append(["title":"Perfil", "icon":"icon_profile"])
        arrayMenuOptions.append(["title":"Preferencias", "icon":"icon_preferences"])
        arrayMenuOptions.append(["title":"Lista de Deseos", "icon":"icon_wish_list"])
        arrayMenuOptions.append(["title":"Invitar", "icon":"icon_invite"])
        arrayMenuOptions.append(["title":"Puntos", "icon":"icon-manage-points"])
        arrayMenuOptions.append(["title":"Preguntas Frecuentes", "icon":"icon_faq"])
        arrayMenuOptions.append(["title":"Notificaciones", "icon":"notificaciones-off"])
        arrayMenuOptions.append(["title":"Cerrar Sesion", "icon":"icon_invite"])
        tblMenuOptions.reloadData()
    }
    
    @IBAction func onCloseMenuClick(button:UIButton!){
        btnMenu.tag = 0
        
        if (self.delegate != nil) {
            
            var index = Int32(button.tag)
            if(button == self.btnCloseMenuOverlay){
                index = -1
            }
            delegate?.slideMenuItemSelectedAtIndex(index)
        }
        
        UIView.animateWithDuration(0.3, animations: { () -> Void in
            self.view.frame = CGRectMake(-UIScreen.mainScreen().bounds.size.width, 0, UIScreen.mainScreen().bounds.size.width,UIScreen.mainScreen().bounds.size.height)
            self.view.layoutIfNeeded()
            self.view.backgroundColor = UIColor.clearColor()
            }, completion: { (finished) -> Void in
                self.view.removeFromSuperview()
                self.removeFromParentViewController()
        })
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
      
        let cell = tableView.dequeueReusableCellWithIdentifier("cellMenu", forIndexPath: indexPath) as UITableViewCell
        cell.selectionStyle = UITableViewCellSelectionStyle.None
        cell.layoutMargins = UIEdgeInsetsZero
        cell.preservesSuperviewLayoutMargins = false
        cell.backgroundColor = UIColor.clearColor()
        
        cell.imageView!.image = UIImage(named: arrayMenuOptions[indexPath.row]["icon"]!)
        cell.textLabel!.text = arrayMenuOptions[indexPath.row]["title"]!
        cell.textLabel!.font = UIFont.systemFontOfSize(10.0)
      
        return cell
    }
    
   
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        let btn = UIButton(type: UIButtonType.Custom)
        btn.tag = indexPath.row
        self.onCloseMenuClick(btn)
       
        if(indexPath.row == positionLogOut-1)
        {
            let appDomain = NSBundle.mainBundle().bundleIdentifier!
            NSUserDefaults.standardUserDefaults().removePersistentDomainForName(appDomain)
            let defaults = NSUserDefaults.standardUserDefaults()
            defaults.setObject("", forKey: "username")
            let vc = self.storyboard!.instantiateViewControllerWithIdentifier("ViewController")
            self.showDetailViewController(vc as! ViewController, sender: self)
            
        }
        
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        self.positionLogOut = arrayMenuOptions.count
        return arrayMenuOptions.count
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1;
    }
    
}
