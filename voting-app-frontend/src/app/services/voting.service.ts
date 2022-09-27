import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CandidateData } from '../datatypes/candidate-data';
import { VoterData } from '../datatypes/voter-data';

@Injectable({
  providedIn: 'root',
})
export class VotingService {
  constructor(private http: HttpClient) {
    http.options;
  }

  getVoters(): Observable<Array<VoterData>> {
    const url = `${environment.apiBaseUrl}/get-voters`;
    return this.http.get<Array<VoterData>>(url);
  }

  getAvailableVoterNames(): Observable<Array<string>> {
    const url = `${environment.apiBaseUrl}/get-available-voter-names`;
    return this.http.get<Array<string>>(url);
  }

  getCandidates(): Observable<Array<CandidateData>> {
    const url = `${environment.apiBaseUrl}/get-candidates`;
    return this.http.get<Array<CandidateData>>(url);
  }

  getCandidateNames(): Observable<Array<string>> {
    const url = `${environment.apiBaseUrl}/get-candidate-names`;
    return this.http.get<Array<string>>(url);
  }

  createVoter(name: string): Observable<string> {
    const url = `${environment.apiBaseUrl}/create-voter/${name}`;
    return this.http.post(url, null, { responseType: 'text' });
  }

  createCandidate(name: string): Observable<string> {
    const url = `${environment.apiBaseUrl}/create-candidate/${name}`;
    return this.http.post(url, null, { responseType: 'text' });
  }

  vote(voterSelect: string, candidateSelect: string): Observable<string> {
    const url = `${environment.apiBaseUrl}/vote?voterName=${voterSelect}&candidateName=${candidateSelect}`;
    return this.http.patch(url, null, { responseType: 'text' });
  }
}
