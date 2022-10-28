/// file/component: Song
/// description: A song object based on our EER model
/// Adrian Burgos - awb8593

export interface Song {
  songId: number,
  title: string,
  runtime: number,
  releaseDate: Date
}
