//
//  MenuViewController.swift
//  BeakonIOS
//
//  Created by Christopher on 8/25/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
protocol SlideMenuDelegate {
    func slideMenuItemSelectedAtIndex(index : Int32)
}

class MenuViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    /**
     *  Array to display menu options
     */
    @IBOutlet var tblMenuOptions : UITableView!
    
    /**
     *  Transparent button to hide menu
     */
    @IBOutlet var btnCloseMenuOverlay : UIButton!
    
    /**
     *  Array containing menu options
     */
    var arrayMenuOptions = [Dictionary<String,String>]()
    
    /**
     *  Menu button which was tapped to display the menu
     */
    var btnMenu: UIButton!
    
    /**
     *  Delegate of the MenuVC
     */
    var delegate : SlideMenuDelegate?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tblMenuOptions.tableFooterView = UIView()
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
      
        return cell
    }
    
   
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        let btn = UIButton(type: UIButtonType.Custom)
        btn.tag = indexPath.row
        self.onCloseMenuClick(btn)
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return arrayMenuOptions.count
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1;
    }
}
