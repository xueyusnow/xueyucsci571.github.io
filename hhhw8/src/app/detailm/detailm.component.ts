import { Component, OnInit, ViewChild, OnDestroy} from '@angular/core';
import {DataService} from '../data.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {NgbAlert} from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';
import { ActivatedRoute,Router,NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-detailm',
  templateUrl: './detailm.component.html',
  styleUrls: ['./detailm.component.css'],
})

export class DetailmComponent implements OnInit ,OnDestroy{
  detail:any=[];
  credit:any=[];
  review:any=[];
  video:any=[];
  genres:string="";
  person:any=[];
  extperson:any=[];
  recommendation:any=[];
  similar:any=[];
  id!:number;
  text:string="";
  apiLoaded = false;
  button:string="Add to Watchlist";
  subscription!:Subscription;
  mobile=false;
  // private _success = new Subject<string>();

  staticAlertClosed = true;
  staticAlertOpen = true;
  constructor(private dataService: DataService,private route: ActivatedRoute,private router:Router,private modalService: NgbModal) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }
  @ViewChild('staticAlert', {static: false}) staticAlert!: NgbAlert;
  @ViewChild('selfClosingAlert', {static: false}) selfClosingAlert!: NgbAlert;
  ngOnInit() {
    if (window.screen.width === 414) { // 768px portrait
      this.mobile = true;
      console.log(this.mobile);
    }
    window.scrollTo(0, 0);
    this.sub();
    this.f0();
    this.f1();
    this.f2();
    this.f3();
    this.f4();
    this.f5();
    this.f6();
    this.f7();
    this.f8();
    this.f9();
    if(this.video.length>0){
      this.text=this.text+"Watch "+this.detail[0].title+"/n"+this.video[0].url+"/n"+"#USC #CSCI571 #FightOn";
    }else{
      this.text=this.text+"Watch "+this.detail[0].title+"/n"+"#USC #CSCI571 #FightOn";
    }
    console.log("success");
  }
  f9(){
    if(localStorage.hasOwnProperty('*'+this.id)){
      this.button="Remove from Watchlist";
    }else{
      this.button="Add to Watchlist";
    }
  }
  sub(){
    this.subscription=this.dataService.currentid.subscribe(id=>this.id=id);
    this.subscription=this.dataService.currentbutton.subscribe(button=>this.button=button);
  }
  ngOnDestroy(){
    if(this.subscription){
      this.subscription.unsubscribe();
    }
  }
  f0(){
      this.id= this.route.snapshot.params['id'];
  }
  f1(){
    var url='http://localhost:3000/detail/';
    url+=this.id;
    this.dataService.sendGetRequest(url).subscribe(data=>{
      console.log(data);
      this.detail=data;
    });  
  }
  f2() {
    var url='http://localhost:3000/credits/';
    url+=this.id;
    this.dataService.sendGetRequest(url).subscribe(data=>{
      console.log(data);
      this.credit=data;
    }); 
  }
  f3() {
    var url='http://localhost:3000/reviews/';
    url+=this.id;
    this.dataService.sendGetRequest(url).subscribe(data=>{
      console.log(data);
      this.review=data;
    }); 
  }
  f4() {
    var url='http://localhost:3000/recommendation/';
    url+=this.id;
    this.dataService.sendGetRequest(url).subscribe(data=>{
      console.log(data);
      this.recommendation=data;
    }); 
  }
  f5() {
    var url='http://localhost:3000/similar/';
    url+=this.id;
    this.dataService.sendGetRequest(url).subscribe(data=>{
      console.log(data);
      this.similar=data;
    }); 
  }
  f6() {
    var url='http://localhost:3000/videos/';
    url+=this.id;
    this.dataService.sendGetRequest(url).subscribe(data=>{
      console.log(data);
      this.video=data;
      // console.log(this.video);
    }); 
  }
  updateperson(person:any){
    this.person=person;
    // console.log(this.person);
  }
  updatepersonext(personext:any){
    this.extperson=personext;
    // console.log(this.person);
  }
  openBackDropCustomClass(content:any) {

    this.modalService.open(content,{windowClass: 'dark-modal',size: 'lg'});
  }
  update(){
    this.ngOnInit();
    window.scrollTo(0, 0);
  }
  
  updatebutton(){
    this.staticAlertClosed=false;
    this.staticAlertOpen=false;
    setTimeout(() => this.staticAlert.close(), 5000);
    setTimeout(() => this.selfClosingAlert.close(), 5000);
    if(this.button=="Add to Watchlist"){
      this.button="Remove from Watchlist";
    }else{
      this.button="Add to Watchlist";
    }
    if(localStorage.hasOwnProperty('*'+this.id)){
      localStorage.removeItem('*'+this.id);
      // console.log(this.id);
    }else{
      var d=new Date().valueOf();
      localStorage.setItem('*'+this.id,String(d));
    }
    // console.log(localStorage);
  }
  f7() {
    if (!this.apiLoaded) {
      // This code loads the IFrame Player API code asynchronously, according to the instructions at
      // https://developers.google.com/youtube/iframe_api_reference#Getting_Started
      const tag = document.createElement('script');
      tag.src = 'https://www.youtube.com/iframe_api';
      document.body.appendChild(tag);
      this.apiLoaded = true;
    }
  }
  f8(){
    for(var i=0;i<Number(localStorage.n);i++){
      if(localStorage.hasOwnProperty(String(i))&&(<string>localStorage.getItem(String(i)))=='*'+String(this.id)){
        for(var j=i;j<Number(localStorage.n);j++){
          if(localStorage.hasOwnProperty(String(j+1))&&localStorage.getItem(String(j+1))!='null'){
            localStorage.setItem(String(j),<string>localStorage.getItem(String(j+1)));
          }else{
            break;
          }
        }
        localStorage.setItem('n',String(Number(localStorage.n)-1));
      }
    }
    
    let str=localStorage.n;
    localStorage.setItem(str,'*'+String(this.id));
    localStorage.setItem('n',String(Number(localStorage.n)+1));
    if(Number(localStorage.n)>24){
      localStorage.removeItem(String(Number(localStorage.n)-25));
    }
    if(Number(localStorage.n)==48){
      for(var i=0;i<24;i++){
        localStorage.setItem(String(i),<string>localStorage.getItem(String(i+24)));
        localStorage.removeItem(String(i+24));
      }
      localStorage.n="24";
    }
    // console.log(localStorage);
  }
}
