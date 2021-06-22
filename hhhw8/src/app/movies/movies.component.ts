import { Component, OnInit } from '@angular/core';
import {DataService} from '../data.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent implements OnInit {
  popular:any=[];
  toprated:any=[];
  trending:any=[];
  mobile=false;
  constructor(private dataService: DataService) { }

  ngOnInit() {
    if (window.screen.width === 414) { // 768px portrait
      this.mobile = true;
      console.log(this.mobile);
    }
    this.f1();
    this.f2();
    this.f3();
  }
  f1(){
    this.dataService.sendGetRequest('http://localhost:3000/popular').subscribe(data=>{
      console.log(data);
      this.popular=data;
    });  
  }
  f2() {

    this.dataService.sendGetRequest('http://localhost:3000/toprated').subscribe(data=>{
      console.log(data);
      this.toprated=data;
    }); 
  }
  f3() {

    this.dataService.sendGetRequest('http://localhost:3000/trending').subscribe(data=>{
      console.log(data);
      this.trending=data;
    }); 
  }
}