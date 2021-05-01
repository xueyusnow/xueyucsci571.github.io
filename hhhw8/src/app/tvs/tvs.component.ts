import { Component, OnInit } from '@angular/core';
import {DataService} from '../data.service';
@Component({
  selector: 'app-tvs',
  templateUrl: './tvs.component.html',
  styleUrls: ['./tvs.component.css']
})
export class TvsComponent implements OnInit {
  popular:any=[];
  toprated:any=[];
  trending:any=[];
  constructor(private dataService: DataService) { }

  ngOnInit() {
    this.f1();
    this.f2();
    this.f3();
  }
  f1(){
    this.dataService.sendGetRequest('http://localhost:3000/populartv').subscribe(data=>{
      console.log(data);
      this.popular=data;
    });  
  }
  f2() {

    this.dataService.sendGetRequest('http://localhost:3000/topratedtv').subscribe(data=>{
      console.log(data);
      this.toprated=data;
    }); 
  }
  f3() {

    this.dataService.sendGetRequest('http://localhost:3000/trendingtv').subscribe(data=>{
      console.log(data);
      this.trending=data;
    }); 
  }
}
