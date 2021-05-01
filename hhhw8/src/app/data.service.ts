import { Injectable } from '@angular/core';
import{HttpClient} from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private httpClient: HttpClient) { }
  private idSource = new BehaviorSubject(0);
  currentid = this.idSource.asObservable();
  private buttonSource = new BehaviorSubject("Add to Watchlist");
  currentbutton = this.buttonSource.asObservable();

  public sendGetRequest(url:string){
    return this.httpClient.get(url);
    
  }
  public changeid(id:number) {
    this.idSource.next(id);
  }

  public changebutton(butt:string) {
    this.buttonSource.next(butt);
  }
  movieLookup(searchTerm: String):Observable<string[]> {
    let url = 'http://localhost:3000/searches/' + searchTerm;
    return this.httpClient.get<any[]>( url)
        .pipe(
            // return only .name rather than id and name since we're not using it here
            map( kv=> kv),
        );
}
}

