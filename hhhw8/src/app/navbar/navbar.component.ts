import { Component, OnInit , Injectable, ɵɵpureFunction1} from '@angular/core';
import { Router ,ActivatedRoute} from '@angular/router';
import {Observable, OperatorFunction} from 'rxjs';
import {debounceTime, map,switchMap, distinctUntilChanged} from 'rxjs/operators';
import {DataService} from '../data.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})


export class NavbarComponent implements OnInit {
  active = 1;
  public model!: any;
  searches:any=[];
  mobile=false;
  // searches$: Observable<any[]>;
  constructor(public router: Router,private dataService: DataService, private route:ActivatedRoute) { }
  
  ngOnInit(): void {
    if (window.screen.width === 414) { // 768px portrait
      this.mobile = true;
      console.log(this.mobile);
    }
    this.router.events.
    subscribe((event)=>{
        if(location.pathname ==='/') this.active = 1;
        else if(location.pathname == '/mylist') this.active = 2;
        else this.active = 0;
  });
  // setTimeout(() => {
  //   $("#typeahead-template").focus();
  // },200);
  }
  f1(key:string){
    var url='http://localhost:3000/searches/';
    url+=key;
    this.dataService.sendGetRequest(url).subscribe(data=>{
      console.log(data);
      this.searches=data;
    });  
    return this.searches;
  }
  refresh(){
    window.location.reload();
  }
  search: OperatorFunction<string, readonly any[]> = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      // map(term => term === '' ? [] : this.f1(term))
      switchMap( searchText => searchText.trim()==''? []: this.dataService.movieLookup(searchText) )
    );
  formatter = (x: {title: string}) => x.title;
  }
  

