export class Shot {
    public id: number;
    public username: string;
    public date: string;
    public time: number;
    public x: number;
    public y: number;
    public r: number;
    public result: boolean;
    
    constructor(shot: Shot) {
        this.id = shot.id;
        this.date = shot.date;
        this.time = shot.time;
        this.x = shot.x;
        this.y = shot.y;
        this.r = shot.r;
        this.result = shot.result;
        this.username = shot.username;
    }
}